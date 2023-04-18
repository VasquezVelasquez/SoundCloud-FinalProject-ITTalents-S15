package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagPlaylistRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagTrackRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.TagService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController extends AbstractController {

    @Autowired
    private TagService tagService;

    @PostMapping("tracks/{trackId}/tags")
    public TrackInfoDTO addTagsToTrack(@PathVariable int trackId, @RequestBody List<TagDTO> tagDTOs, HttpSession s) {
        int userId = getLoggedId(s);
        TagTrackRequestDTO request = new TagTrackRequestDTO();
        request.setTrackId(trackId);
        request.setTags(tagDTOs);
        return tagService.addTagsToTrack(request, userId);
    }

//    @PostMapping("playlists/{playlistId}/tags")
//    public PlaylistDTO addTagsToPlaylist(@PathVariable int playlistId, @RequestBody List<TagDTO> tagDTOs, HttpSession s) {
//        int userId = getLoggedId(s);
//        TagPlaylistRequestDTO request = new TagPlaylistRequestDTO();
//        request.setPlaylistId(playlistId);
//        request.setTags(tagDTOs);
//        return tagService.addTagsToPlaylist(request, userId);
//    }
}
