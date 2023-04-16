package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackUrlDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.TrackService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;


@RestController
public class TrackController extends AbstractController {

    @Autowired
    TrackService trackService;


    @PostMapping("/tracks")
    public TrackInfoDTO upload(@RequestParam("track")MultipartFile trackFile, @RequestParam String title,
                              @RequestParam String description, @RequestParam boolean isPublic,  HttpSession s) {

        return trackService.upload(trackFile, title, description, isPublic,  getLoggedId(s));
    }

    @PutMapping("/tracks/{trackId}")
    public TrackInfoDTO editTrack(@PathVariable int trackId, @RequestBody TrackEditInfoDTO trackEditDTO, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        return trackService.editTrack(trackId, trackEditDTO,  getLoggedId(s));
    }

    @PostMapping("tracks/{id}/like")
    public TrackInfoDTO likeTrack(@PathVariable int id, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        return trackService.likeTrack(id, getLoggedId(s));

    }

    @DeleteMapping("tracks/{id}")
    public TrackInfoDTO deleteTrack(@PathVariable int id, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        return trackService.deleteTrack(id, getLoggedId(s));

    }

    @GetMapping("tracks/{id}")
    public TrackInfoDTO getTrackById(@PathVariable int id) {
        return trackService.showTrackById(id);
    }

    @SneakyThrows
    @GetMapping("tracks/{url}/play")
    public void playTrack(@PathVariable("url") String url, HttpServletResponse response) {
        File track = trackService.download(url);
        Files.copy(track.toPath(), response.getOutputStream());



    }









}
