package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserBasicInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrackUploadInfoDTO {

    private int id;
    private String title;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime uploadedAt;
    private String description;
    private String trackUrl;
    private UserBasicInfoDTO owner;
    private int plays;
    private List<TagDTO> tags;
}
