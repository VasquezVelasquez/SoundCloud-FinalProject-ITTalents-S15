package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private LocalDateTime uploadedAt;
    @Column
    private String trackUrl;
    @Column
    private String coverPictureUrl;
    @Column
    private boolean isPublic;
    @Column
    private String description;
    @OneToMany(mappedBy = "track")
    private Set<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToMany(mappedBy = "likedTracks")
    private Set<User> likes;

    //TODO
    //@Column
    //private double length;
    //@Column
    //private int plays;

}
