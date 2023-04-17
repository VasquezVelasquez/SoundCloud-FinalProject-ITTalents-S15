package com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {


    @Email(message = "Invalid email")
    private String email;
    //todo add proper message
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Weak pass")
    private String password;

    private String confirmedPassword;

    @NotEmpty(message = "Display name cannot be empty")
    @Size(min = 4, max = 50, message = "Display name must be between 4 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Display name can only contain letters")
    private String displayName;

    @Min(value = 14, message = "You must be at least 14 years old")
    @Max(value = 100, message = "You must be younger than 100 years old")
    private int age;

    @Size(min = 1, max = 1, message = "Gender must be a single character")
    @Pattern(regexp = "[MF]", message = "Gender must be either M or F")
    private String gender;
}
