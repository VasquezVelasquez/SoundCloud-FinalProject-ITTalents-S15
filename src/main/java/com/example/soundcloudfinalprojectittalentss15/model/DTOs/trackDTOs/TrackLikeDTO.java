package com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Setter
    @Getter
    @NoArgsConstructor
    public class TrackLikeDTO extends TrackInfoDTO{

        private boolean isLiked;
}
