package com.santech.mtm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Schema(name = "Request to Login", description = "Represents a request to login to the app.")
public class LoginRequest {


    @Schema(
            description = "Email of the user that wants to login to the app).",
            example = "sfidelibus@gmail.com"
    )
    @NotBlank(message = "Email is needed")
    @Email(message = "User must provide an email")
    private String email;

    @Schema(
            description = "Password of the user that wants to login to the app.",
            example = "12345678Sa!"
    )
    @NotBlank(message = "Password is needed")
    @Size(min=8, message="The password needs to have at least 8 characters")
    private String password;
}
