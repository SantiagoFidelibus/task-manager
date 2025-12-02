package com.santech.mtm.repository;

import com.santech.mtm.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserApp, Long> {

    Optional<UserApp> findByEmailAndActiveTrue(String email);
    Optional<UserApp> findByEmail(String email);
    List<UserApp> findAllByActiveTrue();
    Optional<UserApp> findByIdAndActiveTrue(Long id);


}
