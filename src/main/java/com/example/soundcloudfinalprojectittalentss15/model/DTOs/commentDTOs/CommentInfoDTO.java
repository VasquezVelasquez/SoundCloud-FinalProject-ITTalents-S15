package com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoDTO {

    private int ownerId;
    private String content;
    private LocalDateTime postedAt;


}
