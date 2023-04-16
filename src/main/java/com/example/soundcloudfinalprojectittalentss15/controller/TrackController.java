package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackUrlDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
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
    public TrackUrlDTO upload(@RequestParam("track")MultipartFile trackFile, HttpSession s) {
        if(!isValidAudioFile(trackFile)) {
            throw new BadRequestException("File type not accepted!");
        }
        return trackService.upload(trackFile,  getLoggedId(s));
    }

    @PostMapping("/tracks/{url}/info")
    public TrackDTO uploadTrackInfo(@PathVariable String url, @RequestBody TrackDTO trackDTO, HttpSession s) {
        return trackService.uploadTrackInfo(trackDTO, url, getLoggedId(s));
    }

    @PutMapping("/tracks/{trackId}")
    public TrackDTO editTrack(@PathVariable int trackId, @RequestBody TrackEditInfoDTO trackEditDTO, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        return trackService.editTrack(trackId, trackEditDTO,  getLoggedId(s));
    }




}
