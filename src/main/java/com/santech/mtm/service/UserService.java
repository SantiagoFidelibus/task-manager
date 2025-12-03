package com.santech.mtm.service;

import com.santech.mtm.dto.UserDTO;
import com.santech.mtm.exception.UserAlreadyActiveException;
import com.santech.mtm.exception.UserAlreadyInactiveException;
import com.santech.mtm.exception.UserNotFoundException;
import com.santech.mtm.exception.InvalidPassword;
import com.santech.mtm.dto.LoginRequest;

import java.util.List;

public interface UserService {

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Long id) throws UserNotFoundException, UserAlreadyInactiveException;

    UserDTO findUserByEmail(String email) throws UserNotFoundException, UserAlreadyInactiveException;

    UserDTO createUser(UserDTO user) throws UserAlreadyActiveException;

    UserDTO authenticateUser(LoginRequest request) throws UserNotFoundException, UserAlreadyInactiveException, InvalidPassword;

     void softDeleteUser(Long id) throws UserNotFoundException, UserAlreadyInactiveException;

    UserDTO reactivateUser(Long id) throws UserNotFoundException, UserAlreadyActiveException;

}
