package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String displayName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private int age;
    @Column
    private String gender;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String bio;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime lastLogin;
    @Column
    private String profilePictureUrl;
    @Column
    private String backgroundPictureUrl;
    @Column
    private Boolean isVerified;



}
