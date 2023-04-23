package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;
    @Column
    private String content;
    @Column
    private LocalDateTime postedAt;
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
