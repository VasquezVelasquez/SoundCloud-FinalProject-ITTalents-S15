package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.CreatePlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.EditPlaylistInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.TrackIdDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagSearchDTO;
import com.example.soundcloudfinalprojectittalentss15.services.PlaylistService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaylistController extends AbstractController{


    private final PlaylistService playlistService;

    @PostMapping("/playlists")
    public PlaylistDTO create(@Valid @RequestBody CreatePlaylistDTO dto, @RequestHeader ("Authorization") String authHeader) {
        return playlistService.create(dto, getUserIdFromHeader(authHeader));
    }

    @PostMapping("/playlists/{playlistId}/tracks")
    public PlaylistDTO addTrack(@PathVariable int playlistId, @RequestBody TrackIdDTO dto, @RequestHeader ("Authorization") String authHeader) {
        return playlistService.addTrack(playlistId, dto.getTrackId(), getUserIdFromHeader(authHeader));
    }

    @DeleteMapping("/playlists/{playlistId})")
    public ResponseEntity<String> deletePlaylist(@PathVariable int playlistId, @RequestHeader ("Authorization") String authHeader) {
        return playlistService.deletePlaylist(playlistId, getUserIdFromHeader(authHeader));
    }

    @DeleteMapping("/playlists/{playlistId}/tracks/{trackId}")
    public PlaylistDTO removeTrackById(@PathVariable int playlistId, @PathVariable int trackId, @RequestHeader ("Authorization") String authHeader) {
        return playlistService.removeTrackById(playlistId, trackId, getUserIdFromHeader(authHeader));
    }

    @GetMapping("/users/{id}/playlists")
    public List<PlaylistDTO> getPlaylistsByUserId(@PathVariable int id) {
        return playlistService.getPlaylistsByUserId(id);
    }

    @PutMapping("/playlists/{playlistId}")
    public PlaylistDTO editInfo(@PathVariable int playlistId, @RequestBody EditPlaylistInfoDTO dto, @RequestHeader ("Authorization") String authHeader) {
        return playlistService.editInfo(playlistId, dto, getUserIdFromHeader(authHeader));
    }


    @GetMapping("/playlists/search")
    public List<PlaylistDTO> getPlaylistsByTitle(@RequestParam(required = false) String title) {
        if (title != null) {
            return playlistService.getPlaylistsByTitle(title);
        }
        return null;
    }

    @PostMapping("playlists/{id}/like")
    public PlaylistDTO likePlaylist(@PathVariable int id, @RequestHeader ("Authorization") String authHeader) {
        int userId = getUserIdFromHeader(authHeader);
        return playlistService.likePlaylist(id, userId);
    }

    @GetMapping("/playlists/liked")
    public List<PlaylistDTO> getLikedPlaylists(@RequestHeader ("Authorization") String authHeader) {
        return playlistService.getLikedPlaylists(getUserIdFromHeader(authHeader));
    }
    @PostMapping("/playlists/search")
    public Page<PlaylistDTO> searchPlaylistsByTags(@RequestBody TagSearchDTO request) {
        return playlistService.searchPlaylistsByTags(request);
    }

}
