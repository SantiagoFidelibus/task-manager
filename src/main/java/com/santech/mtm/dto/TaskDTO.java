package com.santech.mtm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "Task DTO", description = "Represents a task of the application.")
public class TaskDTO {

    @Schema(
            description = "Unique identifier of the task (auto-generated).",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(
            description = "Title of the task.",
            example = "Finish math homework"
    )
    @NotBlank(message = "The task requires a title.")
    private String title;

    @Schema(
            description = "Detailed description of the task.",
            example = "I need to finish exercise 1 to 10 from chapter 3.",
            maxLength = 500
    )
    @Size(max = 500, message = "Description must be less than 500 characters.")
    private String description;

    @Schema(
            description = "Optional external reference URL for the task.",
            example = "https://www.url.com/task"
    )
    @URL(message = "Invalid URL format.")
    private String externalUrl;

    @Schema(
            description = "Deadline for the task. Must be present or future.",
            example = "2025-02-15T14:30:00"
    )
    @FutureOrPresent(message = "End date must be in the present or future.")
    private LocalDateTime endDate;

    @Schema(
            description = "Indicates whether the task has already been finished.",
            example = "false",
            defaultValue = "false"
    )
    private boolean hasFinished;

    @Schema(
            description = "Indicates whether the task is still active (not deleted).",
            example = "true",
            defaultValue = "true"
    )
    private boolean active;

    @Schema(
            description = "Creation timestamp of the task (auto-generated).",
            example = "2025-02-10T10:15:30",
            accessMode = Schema.AccessMode.READ_ONLY
    )

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;

    @Schema(
            description = "Last modification timestamp of the task (auto-generated).",
            example = "2025-02-11T08:42:10",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastModifiedDate;

    @Schema(
            description = "Identifier of the user that created the task.",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "A owner must be specified for the task")
    private Long ownerId;

    @Schema(
            description = "Detailed user information (read-only).",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO owner;



    public TaskDTO id(Long id) {
        this.id = id;
        return this;
    }

    public TaskDTO title(String title) {
        this.title = title;
        return this;
    }

    public TaskDTO description(String description) {
        this.description = description;
        return this;
    }

    public TaskDTO externalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
        return this;
    }

    public TaskDTO endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public TaskDTO hasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
        return this;
    }

    public TaskDTO active(boolean active) {
        this.active = active;
        return this;
    }

    public TaskDTO creationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public TaskDTO lastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public TaskDTO ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public TaskDTO owner(UserDTO owner) {
        this.owner = owner;
        return this;
    }
}
