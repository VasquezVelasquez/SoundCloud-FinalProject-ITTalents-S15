package com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TagTrackRequestDTO {
    private int trackId;
    private List<TagDTO> tags;

}
