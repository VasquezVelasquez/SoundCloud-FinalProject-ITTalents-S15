package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserBasicInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserWithoutPasswordDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TrackInfoDTO {

    private int id;
    private String title;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime uploadedAt;
    private String description;
    private String trackUrl;
    private UserBasicInfoDTO owner;
    private int plays;
    private List<TagDTO> tags;
    private int likesCount;
}
