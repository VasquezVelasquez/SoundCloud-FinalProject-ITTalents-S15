package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagPlaylistRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagTrackRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController extends AbstractController {


    private final TagService tagService;

    @PostMapping("tracks/{trackId}/tags")
    public TrackInfoDTO addTagsToTrack(@PathVariable int trackId, @RequestBody List<TagDTO> tagDTOs, @RequestHeader("Authorization") String authHeader) {
        TagTrackRequestDTO request = new TagTrackRequestDTO();
        request.setTrackId(trackId);
        request.setTags(tagDTOs);
        return tagService.addTagsToTrack(request, getUserIdFromHeader(authHeader));
    }


    @PostMapping("playlists/{playlistId}/tags")
    public PlaylistDTO addTagsToPlaylist(@PathVariable int playlistId, @Valid @RequestBody List<TagDTO> tagDTOs,
                                         @RequestHeader ("Authorization") String authHeader) {
        TagPlaylistRequestDTO request = new TagPlaylistRequestDTO();
        request.setPlaylistId(playlistId);
        request.setTags(tagDTOs);
        return tagService.addTagsToPlaylist(request, getUserIdFromHeader(authHeader));
    }
}
