package com.example.soundcloudfinalprojectittalentss15.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserWithoutPasswordDTO {

    private int id;
    private String email;
    private int age;
}
