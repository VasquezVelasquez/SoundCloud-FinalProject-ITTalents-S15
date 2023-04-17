package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TrackEditInfoDTO {

    @Size(min = 4, max = 255, message = "The title must be between 4 and 255 characters")
    private String title;
    @Size(min = 4, max = 500, message = "Display name must be between 4 and 500 characters")
    private String description;
    private boolean isPublic;

}
