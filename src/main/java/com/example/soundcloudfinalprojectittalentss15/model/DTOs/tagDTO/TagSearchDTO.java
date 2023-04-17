package com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class TagSearchDTO {
    private List<String> tags;
    private int page;
}
