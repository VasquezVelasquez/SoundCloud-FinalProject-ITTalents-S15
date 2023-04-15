package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.services.MediaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MediaController extends AbstractController{

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload/user/profile-picture")
    public ResponseEntity<String> uploadUserProfilePicture(@RequestParam("file") MultipartFile file, HttpSession s) {
        mediaService.uploadUserProfilePicture(getLoggedId(s), file);
        return ResponseEntity.ok("Profile picture uploaded successfully.");
    }
}
