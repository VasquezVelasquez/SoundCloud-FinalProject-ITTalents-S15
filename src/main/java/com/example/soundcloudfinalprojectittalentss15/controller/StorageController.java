package com.example.soundcloudfinalprojectittalentss15.controller;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackUploadInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.StorageService;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class StorageController extends AbstractController{

    @Autowired
    private StorageService storageService;

    @PostMapping("/tracks")
    public TrackUploadInfoDTO upload(@RequestParam("track") MultipartFile trackFile, @RequestParam String title,
                                     @RequestParam String description, HttpSession s) {
        return storageService.uploadTrack(trackFile, title, description,  getLoggedId(s));
    }

    @SneakyThrows
    @GetMapping("tracks/{url}/play")
    public ResponseEntity<ByteArrayResource> downloadTrack(@PathVariable("url") String url) {
        byte[] track = storageService.downloadTrack(url);
        ByteArrayResource resource = new ByteArrayResource(track);
        return ResponseEntity
                .ok()
                .contentLength(track.length)
                .header("Content-type", "audio/mpeg")
                .header("Content-disposition", "attachment; filename\"" + url + "\"" )
                .body(resource);
    }

    @DeleteMapping("tracks/{id}")
    public TrackInfoDTO deleteTrack(@PathVariable int id, HttpSession s) {
        return storageService.deleteTrack(id, getLoggedId(s));

    }
}
