package com.example.soundcloudfinalprojectittalentss15.model.DTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class TrackDTO {

    private String title;
    private LocalDateTime uploadedAt;
    private String description;
    private Boolean isPublic;
    private String trackUrl;
    private String ownerName;
}
