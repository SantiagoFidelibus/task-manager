package com.santech.mtm.model.mapper;

import com.santech.mtm.dto.UserDTO;
import com.santech.mtm.model.UserApp;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserApp toEntity(UserDTO userDto){
        UserApp userToEntity = new UserApp();
        userToEntity.setId(userDto.getId());
        userToEntity.setName(userDto.getName());
        userToEntity.setLastname(userDto.getLastname());
        userToEntity.setEmail(userDto.getEmail());
        userToEntity.setPassword(userDto.getPassword());
        return userToEntity;
    }

    public UserDTO toDTO(UserApp userApp){
        UserDTO userDTO = new UserDTO();
        return userDTO.id(userApp.getId())
                .name(userApp.getName())
                .lastname(userApp.getLastname())
                .email(userApp.getEmail())
                .password(userApp.getPassword());
    }
}
