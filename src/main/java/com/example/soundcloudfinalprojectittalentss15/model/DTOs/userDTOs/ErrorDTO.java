package com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {

    private Object msg;
    private int status;
    private LocalDateTime time;

}
