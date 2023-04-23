package com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDTO {

    private String email;
    //TODO proper message
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit," +
                    " one lowercase letter, one uppercase letter, and one special character")
    private String newPassword;
    private String confirmNewPassword;
    private String resetCode;
}