package com.example.soundcloudfinalprojectittalentss15.model.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

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

}
