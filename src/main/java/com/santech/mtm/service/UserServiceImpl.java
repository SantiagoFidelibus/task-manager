package com.santech.mtm.service;

import com.santech.mtm.dto.LoginRequest;
import com.santech.mtm.model.UserApp;
import com.santech.mtm.model.mapper.UserMapper;
import com.santech.mtm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserApp findAllUsers() {
        return null;
    }

    @Override
    public UserApp findUserById(Long id) {
        return null;
    }

    @Override
    public UserApp findUserByEmail(String email) {
        return null;
    }

    @Override
    public UserApp createUser(UserApp user) {
        return null;
    }

    @Override
    public UserApp authenticateUser(LoginRequest request) {
        return null;
    }

    @Override
    public void softDeleteUser(Long id) {
        /* TODO: create the method logic */
    }

    @Override
    public UserApp reactivateUser(Long id) {
        return null;
    }
}
