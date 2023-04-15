package com.example.soundcloudfinalprojectittalentss15.services;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.TrackDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service

public class TrackService extends AbstractService{
    @SneakyThrows
    public String upload(MultipartFile trackFile, int loggedId) {
        String extension = FilenameUtils.getExtension(trackFile.getOriginalFilename());
        String name = UUID.randomUUID().toString() + "." + extension;
        File dir = new File("tracks");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, name);
        Files.copy(trackFile.getInputStream(), file.toPath());
        String url = dir.getName() + File.separator + file.getName();
        return url;
//        Track track = mapper.map(trackDTO, Track.class);
//        track.setTrackUrl(url);
//        track.setUploadedAt(LocalDateTime.now());
//        Optional opt = userRepository.findById(loggedId);
//        track.setOwner((User) opt.get());
//        trackRepository.save(track);
//        return mapper.map(track, TrackDTO.class);






    }
}
