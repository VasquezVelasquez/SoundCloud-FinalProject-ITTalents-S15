package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.CreatePlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.EditPlaylistInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.TrackIdDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.PlaylistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaylistController extends AbstractController{

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/playlists")
    public PlaylistDTO create(@RequestBody CreatePlaylistDTO dto, HttpSession s) {
        return playlistService.create(dto, getLoggedId(s));
    }

    @PostMapping("/playlists/{playlistId}/tracks")
    public PlaylistDTO addTrack(@PathVariable int playlistId, @RequestBody TrackIdDTO dto, HttpSession s) {
        return playlistService.addTrack(playlistId, dto.getTrackId(), getLoggedId(s));
    }

    @DeleteMapping("/playlists/{playlistId})")
    public ResponseEntity<String> deletePlaylist(@PathVariable int playlistId, HttpSession s) {
        return playlistService.deletePlaylist(playlistId, getLoggedId(s));
    }

    @GetMapping("/users/{id}/playlists")
    public List<PlaylistDTO> getPlaylistsByUserId(@PathVariable int id, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        return playlistService.getPlaylistsByUserId(id);
    }

    @PutMapping("/playlists/{playlistId}")
    public PlaylistDTO editInfo(@PathVariable int playlistId, @RequestBody EditPlaylistInfoDTO dto, HttpSession s) {
        return playlistService.editInfo(playlistId, dto, getLoggedId(s));
    }


    @GetMapping("/playlists")
    public List<PlaylistDTO> getPlaylistsByName(@RequestParam(required = false) String name, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        if (name == null) {
            return null;
        } else {
            return playlistService.getPlaylistsByName(name);
        }
    }

}
