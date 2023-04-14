package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "playlists")
public class Playlist {

    @Id
    private int id;
}
