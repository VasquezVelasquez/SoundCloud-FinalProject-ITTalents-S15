package com.example.soundcloudfinalprojectittalentss15.services;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackUrlDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
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
    public TrackUrlDTO upload(MultipartFile trackFile, int loggedId) {
        String extension = FilenameUtils.getExtension(trackFile.getOriginalFilename());
        String name = UUID.randomUUID().toString() + "." + extension;
        File dir = new File("tracks");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, name);
        Files.copy(trackFile.getInputStream(), file.toPath());
        String url = dir.getName() + File.separator + file.getName();
        TrackUrlDTO urlDTO = new TrackUrlDTO();
        urlDTO.setTrackUrl(url);
        return urlDTO;
    }

    public TrackDTO uploadTrackInfo(TrackDTO trackDTO, String url, int loggedId) {
        File dir = new File("tracks");
        File file = new File(dir, url);
        if (!file.exists()) {
            throw new BadRequestException("no track uploaded");
        }
        Track track = mapper.map(trackDTO, Track.class);
        track.setTrackUrl(url);
        track.setUploadedAt(LocalDateTime.now());
        Optional<User> opt = userRepository.findById(loggedId);
        track.setOwner(opt.get());
        trackRepository.save(track);
        return mapper.map(track, TrackDTO.class);
    }

    public TrackDTO editTrack(int trackId, TrackEditInfoDTO trackEditDTO, int loggedId) {
        Optional<Track> opt = trackRepository.findById(trackId);
        if(opt.isEmpty()) {
            throw new BadRequestException("Track doesn't exist.");
        }
        Track track = opt.get();
        if(track.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Not authorized action! ");
        }

        track.setDescription(trackEditDTO.getDescription());
        track.setPublic(trackEditDTO.isPublic());
        track.setTitle(trackEditDTO.getTitle());

        trackRepository.save(track);

        return mapper.map(track, TrackDTO.class);

    }

    public TrackDTO likeTrack(int trackId, int loggedId) {
        Track track = getTrackById(trackId);
        User u = getUserById(loggedId);
        if (u.getLikedTracks().contains(track)) {
            u.getLikedTracks().remove(track);
        } else {
            u.getLikedTracks().add(track);
        }
        userRepository.save(u);

        return mapper.map(track, TrackDTO.class);
    }

    public TrackDTO deleteTrack(int trackId, int loggedId) {
        Track track = getTrackById(trackId);
        if(track.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Action not allowed! ");
        }
        TrackDTO trackDTO = mapper.map(track, TrackDTO.class);
        trackRepository.deleteById(trackId);
        return trackDTO;

    }

    public TrackDTO showTrackById(int id) {
        Track track  = getTrackById(id);
        return mapper.map(track, TrackDTO.class);
    }
}
