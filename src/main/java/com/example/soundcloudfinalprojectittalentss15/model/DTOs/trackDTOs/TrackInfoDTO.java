package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserWithoutPasswordDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TrackInfoDTO {

    private String title;
    private LocalDateTime uploadedAt;
    private String description;
    private String trackUrl;
    private UserWithoutPasswordDTO owner;
    private int plays;
    private List<TagDTO> tags;
}
