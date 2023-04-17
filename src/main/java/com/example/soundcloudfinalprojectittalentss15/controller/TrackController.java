package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.TagService;
import com.example.soundcloudfinalprojectittalentss15.services.TrackService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


@RestController
public class TrackController extends AbstractController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private TagService tagService;


    @PostMapping("/tracks")
    public TrackInfoDTO upload(@RequestParam("track")MultipartFile trackFile, @RequestParam String title,
                              @RequestParam String description, @RequestParam boolean isPublic,  HttpSession s) {
        if(title.length() >= 255) {
            throw new BadRequestException("Content length exceeded. 500 symbols maximum! ");
        }
        if(description.length() >= 500) {
            throw new BadRequestException("Content length exceeded. 500 symbols maximum! ");
        }

        return trackService.upload(trackFile, title, description, isPublic,  getLoggedId(s));
    }

    @PutMapping("/tracks/{trackId}")
    public TrackInfoDTO editTrack(@PathVariable int trackId,@Valid @RequestBody TrackEditInfoDTO trackEditDTO, HttpSession s) {
        int userId = getLoggedId(s);
        return trackService.editTrack(trackId, trackEditDTO, userId);
    }

    @PostMapping("tracks/{id}/like")
    public TrackInfoDTO likeTrack(@PathVariable int id, HttpSession s) {
        int userId = getLoggedId(s);
        return trackService.likeTrack(id, userId);

    }

    @DeleteMapping("tracks/{id}")
    public TrackInfoDTO deleteTrack(@PathVariable int id, HttpSession s) {
        int userId = getLoggedId(s);
        return trackService.deleteTrack(id, userId);

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

    @GetMapping("/users/{userId}/tracks")
    public List<TrackInfoDTO> getAllTracksByUser(@PathVariable int userId) {
        return trackService.getAllTracksByUser(userId);

    }

    @GetMapping("/tracks/search")
    public List<TrackInfoDTO> searchTracksByTitle(@RequestParam("title") String title) {
        return trackService.searchTracksByTitle(title);
    }

    @GetMapping("/tracks")
    public Page<TrackInfoDTO> getAllPublicTracksWithPagination(@RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        return trackService.getAllPublicTracksWithPagination(pageNumber);

    }

    @PostMapping("tracks/{trackId}/tags")
    public TrackInfoDTO addTagsToTrack(@PathVariable int trackId, @RequestBody List<TagDTO> tagDTOs, HttpSession s) {
        int userId = getLoggedId(s);
        TagRequestDTO request = new TagRequestDTO();
        request.setTrackId(trackId);
        request.setTags(tagDTOs);
        return tagService.addTagsToTrack(request, userId);
    }











}
