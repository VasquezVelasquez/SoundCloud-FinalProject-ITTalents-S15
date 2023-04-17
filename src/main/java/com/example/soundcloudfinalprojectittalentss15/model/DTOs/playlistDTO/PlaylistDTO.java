package com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PlaylistDTO {

    private int id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private boolean isPublic;
    private String coverPictureUrl;
    private List<TagDTO> tags;
}
