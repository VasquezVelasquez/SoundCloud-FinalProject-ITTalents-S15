package com.example.soundcloudfinalprojectittalentss15.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Track> tracks;



}
