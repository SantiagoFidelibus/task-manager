package com.santech.mtm.repository;

import com.santech.mtm.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByActiveTrue();
    boolean existsByIdAndActiveTrue(Long id);
    Optional<Task> findByIdAndActiveTrue(Long id);
    List<Task> findByTitleAndOwnerIdAndActiveTrue(String title, Long ownerId);

    @Modifying
    @Query("UPDATE Task t SET t.active = false WHERE t.id = :id AND t.active = true")
    void deactivate(@Param("id") Long id);
}
