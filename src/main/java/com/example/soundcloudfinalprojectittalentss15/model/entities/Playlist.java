package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime lastModified;
    @Column
    private String coverPictureUrl;
    @Column
    private boolean isPublic;
    @Column
    private String description;
}
