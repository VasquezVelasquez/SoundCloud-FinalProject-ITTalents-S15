package com.example.soundcloudfinalprojectittalentss15.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {

    private String email;
    private String password;
    private String confirmedPassword;
    private int age;
    private String gender;

}
