package com.santech.mtm.service;

import com.santech.mtm.dto.TaskDTO;
import com.santech.mtm.exception.TaskNotFoundException;
import com.santech.mtm.model.Task;
import com.santech.mtm.repository.TaskRepository;
import com.santech.mtm.model.mapper.TaskMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Creating a new task: {}", taskDTO.getTitle());
        Task task = taskMapper.toEntity(taskDTO);
        task.setActive(true);
        task.setHasFinished(false);

        Task saved = taskRepository.save(task);

        log.info("Task created successfully with ID {}", saved.getId());
        return taskMapper.toDTO(saved);
    }

    @Override
    public TaskDTO getTaskById(Long id) throws TaskNotFoundException {
        log.info("Searching active task with ID: {}", id);

        Task task = taskRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        () -> new TaskNotFoundException(
                                "Task with ID: " + id + " not found"
                        )
                );

        return taskMapper.toDTO(task);
    }

    @Override
    public List<TaskDTO> getTasksByTitle(String title, Long ownerId) throws TaskNotFoundException{
        List<Task> tasks = taskRepository.findByTitleAndOwnerIdAndActiveTrue(title, ownerId);

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException(
                    "No tasks found for title '" + title + "' and ownerId " + ownerId
            );
        }

        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();

    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAllByActiveTrue()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) throws TaskNotFoundException{
        log.info("Updating task with ID {}", id);
        Task updated = createUpdateTask(findActiveTaskOrThrow(id), taskDTO);

        taskRepository.save(updated);
        log.info("Task updated successfully with ID {}", updated.getId());

        return taskMapper.toDTO(updated);
    }


    @Override
    @Transactional
    public TaskDTO markAsFinished(Long id) throws TaskNotFoundException{
        log.info("Marking task as finished: {}", id);
        Task task = findActiveTaskOrThrow(id);

        if(task.isHasFinished()) {
            log.warn("Task is already finished: {}", id);
            return taskMapper.toDTO(task);
        }

        task.setHasFinished(true);
        taskRepository.save(task);

        log.info("Task with ID {} marked as finished", task.getId());
        return taskMapper.toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO markAsUnfinished(Long id) throws TaskNotFoundException{
        log.info("Marking task as unfinished: {}", id);
        Task task = findActiveTaskOrThrow(id);

        if(!task.isHasFinished()) {
            log.warn("Task is not finished: {}", id);
            return taskMapper.toDTO(task);
        }

        task.setHasFinished(false);
        taskRepository.save(task);

        log.info("Task with ID {} marked as unfinished", id);
        return taskMapper.toDTO(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) throws TaskNotFoundException{
        boolean exists = taskRepository.existsByIdAndActiveTrue(id);

        if (!exists) {
            throw new TaskNotFoundException(
                    "Verify that id (" + id + ") refers to an existing active task."
            );
        }
        taskRepository.deactivate(id);
    }

    private Task findActiveTaskOrThrow(Long id) throws TaskNotFoundException {
        return taskRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new TaskNotFoundException(
                        "No active task found with ID: " + id
                )
        );
    }

    private Task createUpdateTask(Task oldTask, TaskDTO newTask){
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setExternalUrl(newTask.getExternalUrl());
        oldTask.setEndDate(newTask.getEndDate());
        return oldTask;
    }
}


