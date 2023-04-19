package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagSearchDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.TrackService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class TrackController extends AbstractController {

    @Autowired
    private TrackService trackService;


    @PutMapping("/tracks/{trackId}")
    public TrackInfoDTO editTrack(@PathVariable int trackId, @Valid @RequestBody TrackEditInfoDTO trackEditDTO, HttpSession s) {
        int userId = getLoggedId(s);
        return trackService.editTrack(trackId, trackEditDTO, userId);
    }

    @PostMapping("tracks/{id}/like")
    public TrackInfoDTO likeTrack(@PathVariable int id, HttpSession s) {
        int userId = getLoggedId(s);
        return trackService.likeTrack(id, userId);

    }



    @GetMapping("tracks/{id}")
    public TrackInfoDTO getTrackById(@PathVariable int id) {
        return trackService.showTrackById(id);
    }


    @GetMapping("/users/{userId}/tracks")
    public List<TrackInfoDTO> getAllTracksByUser(@PathVariable int userId) {
        return trackService.getAllTracksByUser(userId);

    }

    @GetMapping("/tracks/search")
    public List<TrackInfoDTO> searchTracksByTitle(@RequestParam("title") String title) {
        return trackService.searchTracksByTitle(title);
    }

    @GetMapping("/tracks")
    public Page<TrackInfoDTO> getAllTracksWithPagination(@RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        return trackService.getAllTracksWithPagination(pageNumber);
    }

    @PostMapping("tracks/search")
    public Page<TrackInfoDTO> searchTracksByTags(@RequestBody TagSearchDTO request) {
        return trackService.searchTracksByTags(request);

    }











}
