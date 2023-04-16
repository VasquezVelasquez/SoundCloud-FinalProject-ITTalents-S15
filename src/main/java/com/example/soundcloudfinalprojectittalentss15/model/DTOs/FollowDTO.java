package com.example.soundcloudfinalprojectittalentss15.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class FollowDTO {

    private int followerId;
    private String followerDisplayName;
    private int followedUserId;
    private String followedUserDisplayName;
    private LocalDateTime followEventAt;
    private boolean isFollowing;

    // Constructors, getters, and setters
}
