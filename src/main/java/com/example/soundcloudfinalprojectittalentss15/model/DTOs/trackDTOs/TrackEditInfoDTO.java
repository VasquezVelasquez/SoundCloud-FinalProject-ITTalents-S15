package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TrackEditInfoDTO {

    private String title;
    private String description;
    private boolean isPublic;

}
