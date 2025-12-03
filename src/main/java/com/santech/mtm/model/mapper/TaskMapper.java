package com.santech.mtm.model.mapper;

import com.santech.mtm.dto.TaskDTO;
import com.santech.mtm.model.Task;

import com.santech.mtm.model.UserApp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TaskMapper {

    private final UserMapper userMapper;

    public Task toEntity(TaskDTO taskDTO){
        Task  task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        Optional.ofNullable(taskDTO.getExternalUrl())
                .ifPresent(task::setExternalUrl);
        Optional.ofNullable(taskDTO.getEndDate())
                .ifPresent(task::setEndDate);
        UserApp owner = new UserApp();
        owner.setId(taskDTO.getOwnerId());
        task.setOwner(owner);
        return task;
    }

    public TaskDTO toDTO(Task taskEntity){
        TaskDTO taskDTO = new TaskDTO()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .hasFinished(taskEntity.isHasFinished())
                .active(taskEntity.isActive())
                .creationDate(taskEntity.getCreationDate())
                .lastModifiedDate(taskEntity.getLastModifiedDate())
                .owner(userMapper.toDTO(taskEntity.getOwner()));

        Optional.ofNullable(taskEntity.getExternalUrl())
                .ifPresent(taskDTO::externalUrl);

        Optional.ofNullable(taskEntity.getEndDate())
                .ifPresent(taskDTO::endDate);

        return taskDTO;
    }

}
