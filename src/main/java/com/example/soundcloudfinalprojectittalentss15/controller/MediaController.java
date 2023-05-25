package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
public class MediaController extends AbstractController{


    private final MediaService mediaService;

    @PostMapping("/users/upload/profile-pic")
    public UserWithoutPasswordDTO uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader) {
        return mediaService.uploadProfilePicture(file, getUserIdFromHeader(authHeader));
    }

    @PostMapping("/users/upload/background-pic")
    public UserWithoutPasswordDTO uploadBackgroundPicture(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader) {
        return mediaService.uploadBackgroundPicture(file, getUserIdFromHeader(authHeader));
    }

    @SneakyThrows
    @GetMapping("/media/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = mediaService.download(fileName);

        resp.setContentType("image/avif");
        Files.copy(f.toPath(), resp.getOutputStream());
    }


    @PostMapping("/tracks/{trackId}")
    public TrackInfoDTO uploadTrackCoverPicture(@RequestParam("file") MultipartFile file, @PathVariable int trackId, @RequestHeader("Authorization") String authHeader) {

        return mediaService.uploadTrackCoverPicture(file, trackId, getUserIdFromHeader(authHeader));
    }


    @PostMapping("/playlists/{playlistId}/cover-pic")
    public PlaylistDTO uploadPlaylistCoverPicture(@RequestParam("file") MultipartFile file,
                                                  @PathVariable int playlistId, @RequestHeader("Authorization") String authHeader) {
        return mediaService.uploadPlaylistCoverPicture(file, playlistId, getUserIdFromHeader(authHeader));
    }


}
