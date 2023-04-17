package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserWithoutPasswordDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class TrackInfoDTO {

    private String title;
    private LocalDateTime uploadedAt;
    private String description;
    private boolean isPublic;
    private String trackUrl;
    private UserWithoutPasswordDTO owner;
    private int plays;
}
