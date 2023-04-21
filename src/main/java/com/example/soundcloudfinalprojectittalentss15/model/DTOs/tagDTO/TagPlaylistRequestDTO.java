package com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TagPlaylistRequestDTO {
    private int playlistId;
    private List<TagDTO> tags;
}
