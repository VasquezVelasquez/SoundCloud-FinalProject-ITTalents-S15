package com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserWithoutPasswordDTO {

    private int id;
    private String email;
    private String displayName;
    private int age;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String backgroundPictureUrl;
}
