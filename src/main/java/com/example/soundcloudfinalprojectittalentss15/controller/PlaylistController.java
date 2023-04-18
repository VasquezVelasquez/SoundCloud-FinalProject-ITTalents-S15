package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.CreatePlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.EditPlaylistInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.TrackIdDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagPlaylistRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagSearchDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagTrackRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.PlaylistService;
import com.example.soundcloudfinalprojectittalentss15.services.TagService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaylistController extends AbstractController{

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private TagService tagService;

    @PostMapping("/playlists")
    public PlaylistDTO create(@Valid @RequestBody CreatePlaylistDTO dto, HttpSession s) {
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

    @DeleteMapping("/playlists/{playlistId}/tracks/{trackId}")
    public PlaylistDTO removeTrackById(@PathVariable int playlistId, @PathVariable int trackId, HttpSession s) {
        return playlistService.removeTrackById(playlistId, trackId, getLoggedId(s));
    }

    @GetMapping("/users/{id}/playlists")
    public List<PlaylistDTO> getPlaylistsByUserId(@PathVariable int id, HttpSession s) {
        checkLogged(s);
        return playlistService.getPlaylistsByUserId(id);
    }

    @PutMapping("/playlists/{playlistId}")
    public PlaylistDTO editInfo(@PathVariable int playlistId, @RequestBody EditPlaylistInfoDTO dto, HttpSession s) {
        return playlistService.editInfo(playlistId, dto, getLoggedId(s));
    }


    @GetMapping("/playlists")
    public List<PlaylistDTO> getPlaylistsByName(@RequestParam(required = false) String name, HttpSession s) {
        checkLogged(s);
        if (name != null) {
            return playlistService.getPlaylistsByName(name);
        }
        return null;
    }

    @PostMapping("playlists/{id}/like")
    public PlaylistDTO likePlaylist(@PathVariable int id, HttpSession s) {
        int userId = getLoggedId(s);
        return playlistService.likePlaylist(id, userId);
    }

    @GetMapping("/playlists/liked")
    public List<PlaylistDTO> getLikedPlaylists(HttpSession s) {
        int id = getLoggedId(s);
        return playlistService.getLikedPlaylists(id);
    }


    //TODO add validation on tagsDTO
    @PostMapping("playlists/{playlistId}/tags")
    public PlaylistDTO addTagsToPlaylist(@PathVariable int playlistId, @RequestBody List<TagDTO> tagDTOs, HttpSession s) {
        int userId = getLoggedId(s);
        TagPlaylistRequestDTO request = new TagPlaylistRequestDTO();
        request.setPlaylistId(playlistId);
        request.setTags(tagDTOs);
        return tagService.addTagsToPlaylist(request, userId);
    }


    @PostMapping("playlists/search")
    public Page<PlaylistDTO> searchTracksByTags(@RequestBody TagSearchDTO request) {
        return playlistService.searchPlaylistsByTags(request);
    }

}
