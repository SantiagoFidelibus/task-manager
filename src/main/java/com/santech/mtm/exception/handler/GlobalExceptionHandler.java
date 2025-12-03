package com.santech.mtm.exception.handler;

import com.santech.mtm.exception.InvalidPasswordException;
import com.santech.mtm.exception.UserAlreadyActiveException;
import com.santech.mtm.exception.UserAlreadyInactiveException;
import com.santech.mtm.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
    UserActive -> 409
    UserInactive -> 409
     */

    @ApiResponse(
            responseCode = "400",
            description = "Invalid Password",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "12345678 != password")
            )
    )
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPassword(InvalidPasswordException ex) {
        log.warn("400 Validation failed. errors" + ex.getMessage());
        return ResponseEntity.badRequest().body(this.errorMsg(ex.getMessage()));

    }


    @ApiResponse(
            responseCode = "400",
            description = "Validation error in the request body",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "The user requires an email")
            )
    )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        log.warn("400 Bad Request: "+ ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error/s:", errors));
    }

    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "User with id 1 does not exist or is inactive.")
            )
    )
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUserNotFound(UserNotFoundException ex) {
        log404(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(this.errorMsg(ex.getMessage()));
    }

    @ApiResponse(
            responseCode = "409",
            description = "The user is already active",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Cannot reactivate user: user already active.")
            )
    )
    @ExceptionHandler(UserAlreadyActiveException.class)
    public ResponseEntity<Map<String,String>> handleUserAlreadyActive(UserAlreadyActiveException ex) {
        log409(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(this.errorMsg(ex.getMessage()));
    }

    @ApiResponse(
            responseCode = "409",
            description = "The user is already inactive",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Cannot delete: user already inactive.")
            )
    )
    @ExceptionHandler(UserAlreadyInactiveException.class)
    public ResponseEntity<Map<String,String>> handleUserAlreadyInactive(UserAlreadyInactiveException ex) {
        log409(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(this.errorMsg(ex.getMessage()));
    }

    @ApiResponse(
            responseCode = "500",
            description = "Unchecked runtime exception",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "500 Internal Server Error at: {exceptionMessage}")
            )
    )
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        log500(ex);
        return ResponseEntity.internalServerError().body(Map.of("500 Internal Server Error at: ", ex.getMessage()));
    }

    @ApiResponse(
            responseCode = "500",
            description = "Unhandled exception",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "500 Internal Server Error at: {exceptionMessage}")
            )
    )
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        log500(ex);
        return ResponseEntity.internalServerError().body(Map.of("500 Internal Server Error at: ", ex.getMessage()));
    }

    public void log404(Exception ex){
        log.warn("404 Not Found: "+ ex.getMessage());
    }
    public void log409(Exception ex){
        log.warn("409 Conflict: "+ ex.getMessage());
    }
    public void log500(Exception ex){
        log.warn("500 Internal Server Error: "+ ex.getMessage());
    }

    public Map<String,String> errorMsg(String msg){
        return Map.of("error", msg);
    }

}
