package com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePlaylistDTO {


    @Size(min = 2, max = 50, message = "Title name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Title name can only contain letters and digits")
    private String title;

    private boolean isPublic;

    private int trackId;
}
