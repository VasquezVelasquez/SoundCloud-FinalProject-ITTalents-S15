package com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagDTO {

    @Size(min = 4, max = 50, message = "The title must be between 4 and 255 characters")
    private String name;
}
