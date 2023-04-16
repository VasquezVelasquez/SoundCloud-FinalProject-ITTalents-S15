package com.example.soundcloudfinalprojectittalentss15.services;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class TrackService extends AbstractService{
    @SneakyThrows
    public TrackInfoDTO upload(MultipartFile trackFile, String title, String description, boolean isPublic, int loggedId) {
        if(!isValidAudioFile(trackFile)) {
            throw new BadRequestException("File type not accepted, you should select a mp3 file!");
        }
        String name = createFileName(trackFile);
        String url = createFile(trackFile, name, "tracks");

        Track track = new Track();
        track.setTitle(title);
        track.setUploadedAt(LocalDateTime.now());
        track.setTrackUrl(url);
        track.setPublic(isPublic);
        track.setDescription(description);
        track.setOwner(getUserById(loggedId));
        track.setPlays(0);
        trackRepository.save(track);
        return mapper.map(track, TrackInfoDTO.class);
    }


    public TrackInfoDTO editTrack(int trackId, TrackEditInfoDTO trackEditDTO, int loggedId) {
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

        return mapper.map(track, TrackInfoDTO.class);

    }

    public TrackInfoDTO likeTrack(int trackId, int loggedId) {
        Track track = getTrackById(trackId);
        User u = getUserById(loggedId);
        if (u.getLikedTracks().contains(track)) {
            u.getLikedTracks().remove(track);
        } else {
            u.getLikedTracks().add(track);
        }
        userRepository.save(u);

        return mapper.map(track, TrackInfoDTO.class);
    }

    public TrackInfoDTO deleteTrack(int trackId, int loggedId) {
        Track track = getTrackById(trackId);
        if(track.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Action not allowed! ");
        }
        TrackInfoDTO trackInfoDTO = mapper.map(track, TrackInfoDTO.class);
        trackRepository.deleteById(trackId);
        return trackInfoDTO;

    }

    public TrackInfoDTO showTrackById(int id) {
        Track track  = getTrackById(id);
        return mapper.map(track, TrackInfoDTO.class);
    }
}
