package com.example.soundcloudfinalprojectittalentss15.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserBasicInfoDTO {

    private int id;
    private String email;
    private int age;
    private String profileImageUrl;

}