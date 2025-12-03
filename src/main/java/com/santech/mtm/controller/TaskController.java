package com.santech.mtm.controller;

import com.santech.mtm.dto.TaskDTO;
import com.santech.mtm.exception.TaskNotFoundException;
import com.santech.mtm.service.TaskService;
import com.santech.mtm.swagger.InternalServerErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Tag(name = "Tasks",description = "Manage user tasks")
@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;


    @Operation(summary = "Get all tasks")
    @ApiResponse(responseCode = "200", description = "List of all tasks")
    @InternalServerErrorResponse
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {

        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get task by id")
    @ApiResponse(responseCode = "200", description = "List task")
    @ApiResponse(responseCode = "404", description = "Task not founded")
    @InternalServerErrorResponse
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Get tasks by title")
    @ApiResponse(responseCode = "200", description = "List of tasks with some title")
    @ApiResponse(responseCode = "404", description = "Task not founded")
    @InternalServerErrorResponse
    @GetMapping("/{ownerId}/{title}")
    public ResponseEntity<List<TaskDTO>> getByTitle(@PathVariable Long ownerId,@PathVariable String title) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.getTasksByTitle(title,ownerId));
    }

    @Operation(summary = "Create a new task")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Validation fail",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }")
            )
    )
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO created = taskService.createTask(taskDTO);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Delete tasks")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not founded")
    @InternalServerErrorResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws TaskNotFoundException {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a task")
    @ApiResponse(responseCode = "200", description = "Task updated")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @InternalServerErrorResponse
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) throws TaskNotFoundException {
        TaskDTO updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Mark a task as finished")
    @ApiResponse(responseCode = "200", description = "Task marked as finished")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @InternalServerErrorResponse
    @PatchMapping("/{id}/finish")
    public ResponseEntity<TaskDTO> finishTask(@PathVariable Long id) throws TaskNotFoundException {
        TaskDTO result = taskService.markAsFinished(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Mark a task as unfinished")
    @ApiResponse(responseCode = "200", description = "Task marked as unfinished")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @InternalServerErrorResponse
    @PatchMapping("/{id}/unfinish")
    public ResponseEntity<TaskDTO> unfinishTask(@PathVariable Long id) throws TaskNotFoundException {
        TaskDTO result = taskService.markAsUnfinished(id);
        return ResponseEntity.ok(result);
    }

}
