package com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PlaylistDTO {

    private int id;
    private String title;
    private LocalDateTime createdAt;
    private boolean isPublic;
    private String coverPictureUrl;
}
