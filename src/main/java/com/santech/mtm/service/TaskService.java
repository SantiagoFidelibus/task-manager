package com.santech.mtm.service;

import com.santech.mtm.dto.TaskDTO;
import com.santech.mtm.exception.TaskNotFoundException;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO task);

    TaskDTO getTaskById(Long id) throws TaskNotFoundException;

    List<TaskDTO> getTasksByTitle(String title, Long ownerId) throws TaskNotFoundException;

    List<TaskDTO> getAllTasks();

    TaskDTO updateTask(Long id, TaskDTO task) throws TaskNotFoundException;

    TaskDTO markAsFinished(Long id) throws TaskNotFoundException;

    TaskDTO markAsUnfinished(Long id) throws TaskNotFoundException;

    void deleteTask(Long id) throws TaskNotFoundException;

}
