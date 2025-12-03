package com.santech.mtm.controller;

import com.santech.mtm.dto.LoginRequest;
import com.santech.mtm.dto.UserDTO;
import com.santech.mtm.exception.UserAlreadyActiveException;
import com.santech.mtm.exception.UserAlreadyInactiveException;
import com.santech.mtm.exception.UserNotFoundException;
import com.santech.mtm.exception.InvalidPassword;
import com.santech.mtm.service.UserService;
import com.santech.mtm.swagger.InternalServerErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users",description = "Manage user accounts")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController{

    private final UserService userService;

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of all users")
    @InternalServerErrorResponse
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {

        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @InternalServerErrorResponse
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) throws UserNotFoundException, UserAlreadyInactiveException {
        UserDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by email")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @InternalServerErrorResponse

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email) throws UserNotFoundException, UserAlreadyInactiveException {
        UserDTO user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @InternalServerErrorResponse
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO)
            throws UserAlreadyActiveException {

        UserDTO created = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @ApiResponse(responseCode = "200", description = "Session created")
    @InternalServerErrorResponse
    @PostMapping("/login")
    public ResponseEntity<String> login  (@Valid
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request) throws UserNotFoundException, UserAlreadyInactiveException, InvalidPassword {

        UserDTO user = userService.authenticateUser(loginRequest);

        HttpSession session = request.getSession(true);

        session.setAttribute("id", user.getId());
        session.setAttribute("email", user.getEmail());

        session.setMaxInactiveInterval(-1);

        return ResponseEntity.ok("Login exitoso, sesión creada");
    }

    @ApiResponse(responseCode = "200", description = "Session closed")
    @InternalServerErrorResponse
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("Sesión cerrada");
    }

    @Operation(summary = "Soft delete user")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "409", description = "User already inactive")
    @InternalServerErrorResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id)
            throws UserNotFoundException, UserAlreadyInactiveException {

        userService.softDeleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reactivate a previously deleted user")
    @ApiResponse(responseCode = "200", description = "User reactivated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "409", description = "User already active")
    @InternalServerErrorResponse
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<UserDTO> reactivate(@PathVariable Long id)
            throws UserNotFoundException, UserAlreadyActiveException {

        return ResponseEntity.ok(userService.reactivateUser(id));
    }

}
