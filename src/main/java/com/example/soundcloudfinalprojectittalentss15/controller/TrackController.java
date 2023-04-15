package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.TrackDTO;
import com.example.soundcloudfinalprojectittalentss15.services.TrackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class TrackController extends AbstractController {

    @Autowired
    TrackService trackService;


    @PostMapping("/tracks")
    public String upload(@RequestParam("track")MultipartFile trackFile, HttpSession s) {
        return trackService.upload(trackFile,  getLoggedId(s));
    }




}
