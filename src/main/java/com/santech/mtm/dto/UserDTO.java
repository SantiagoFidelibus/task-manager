package com.santech.mtm.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "User DTO", description = "Represents a user of the app.")
public class UserDTO {

    @Schema(
            description = "Unique identifier of the candidate (auto-generated).",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(
            description = "Name of the user.",
            example = "Santiago"
    )
    @NotBlank(message = "A name can't be null")
    private String name;

    @Schema(
            description = "Lastname of the user.",
            example = "Fidelibus"
    )
    @NotBlank(message = "A lastname can't be null")
    private String lastname;


    @Schema(
            description = "Email of the user.",
            example = "sfidelibus@gmail.com"
    )
    @NotBlank(message = "A email can't be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Password of the user.",
            example = "12345678Sa!"
    )
    @NotBlank(message = "A password can't be blank")
    @Size(min=8, message="The password needs to have at least 8 characters")
    private String password;

    public UserDTO id(Long id) {
        this.id = id;
        return this;
    }


    public UserDTO name(String name) {
        this.name = name;
        return this;
    }

    public UserDTO lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserDTO email(String email) {
        this.email = email;
        return this;
    }

    public UserDTO password(String password) {
        this.password = password;
        return this;
    }
}
