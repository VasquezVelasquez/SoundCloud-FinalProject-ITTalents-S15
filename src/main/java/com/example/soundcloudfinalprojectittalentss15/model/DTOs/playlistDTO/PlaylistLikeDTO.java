package com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistLikeDTO extends PlaylistDTO{

    private boolean isLiked;
}
