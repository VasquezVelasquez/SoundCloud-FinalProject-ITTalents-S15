package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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
    private String description;
    @OneToMany(mappedBy = "track")
    private Set<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToMany(mappedBy = "likedTracks")
    private Set<User> likes;
    @Column
    private int plays;
    @ManyToMany(mappedBy = "tracks")
    private Set<Playlist> playlists = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "tracks_have_tags",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    @Column(name = "likes")
    private int likesCount;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return id == track.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
