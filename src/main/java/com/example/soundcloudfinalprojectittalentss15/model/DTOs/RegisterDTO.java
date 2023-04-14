package com.example.soundcloudfinalprojectittalentss15.model.DTOs;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {

    @Email(regexp = ".+@.+\\..+")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String confirmedPassword;
    @Min(value = 14, message = "You must be at least 18 years old")
    @Max(value = 100, message = "You must be younger than 100 years old")
    private int age;

    @Size(min = 1, max = 1, message = "Gender must be a single character")
    @Pattern(regexp = "[MF]", message = "Gender must be either M or F")
    private String gender;
}
