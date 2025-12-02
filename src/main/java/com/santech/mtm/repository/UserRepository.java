package com.santech.mtm.repository;

import com.santech.mtm.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserApp, Long> {

    Optional<UserApp> findByEmail(String email);
}
