package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class MediaController extends AbstractController{

    @Autowired
    private MediaService mediaService;

    @PostMapping("/users/upload/profile-pic")
    public UserWithoutPasswordDTO uploadProfilePicture(@RequestParam("file") MultipartFile file, HttpSession s) {
        return mediaService.uploadProfilePicture(file, getLoggedId(s));
    }

    @PostMapping("/users/upload/background-pic")
    public UserWithoutPasswordDTO uploadBackgroundPicture(@RequestParam("file") MultipartFile file, HttpSession s) {
        return mediaService.uploadBackgroundPicture(file, getLoggedId(s));
    }

    @SneakyThrows
    @GetMapping("/media/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = mediaService.download(fileName);
        //todo incorrect image visualization. I've tried with /*, /jpg
        resp.setContentType("image/avif");
        Files.copy(f.toPath(), resp.getOutputStream());
    }


    @PostMapping("/tracks/{trackId}")
    public TrackInfoDTO uploadTrackCoverPicture(@RequestParam("file") MultipartFile file, @PathVariable int trackId, HttpSession s) {

        return mediaService.uploadTrackCoverPicture(file, trackId, getLoggedId(s));
    }


    @PostMapping("/playlists/{playlistId}/cover-pic")
    public PlaylistDTO uploadPlaylistCoverPicture(@RequestParam("file") MultipartFile file,
                                                  @PathVariable int playlistId, HttpSession s) {
        return mediaService.uploadPlaylistCoverPicture(file, playlistId, getLoggedId(s));
    }


}
