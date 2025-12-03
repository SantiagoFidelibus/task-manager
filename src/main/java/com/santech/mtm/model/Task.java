package com.santech.mtm.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tasks",
        indexes = {
                @Index(name = "idx_task_has_finished", columnList = "hasFinished"),
                @Index(name = "idx_task_end_date", columnList = "endDate"),
                @Index(name = "idx_task_owner", columnList = "user_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter@Setter@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String externalUrl;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean hasFinished = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp owner;

    @Column(nullable = false)
    private boolean active = true;
}

