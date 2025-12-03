package com.santech.mtm.service;

import com.santech.mtm.model.UserApp;
import com.santech.mtm.dto.LoginRequest;

public interface UserService {

    UserApp findAllUsers();

    UserApp findUserById(Long id);

    UserApp findUserByEmail(String email);

    UserApp createUser(UserApp user);

    UserApp authenticateUser(LoginRequest request);

     void softDeleteUser(Long id);

    UserApp reactivateUser(Long id);

}
