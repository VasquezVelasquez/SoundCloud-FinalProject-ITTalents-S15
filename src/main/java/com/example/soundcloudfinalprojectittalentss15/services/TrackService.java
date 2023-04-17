package com.example.soundcloudfinalprojectittalentss15.services;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.NotFoundException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        track.setPlays(1);
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

    public File download(String url) {
        File dir = new File("tracks");
        File track = new File(dir, url);
        if(track.exists()) {
            Track file = trackRepository.findByTrackUrl(dir + File.separator + url);
            file.setPlays(file.getPlays() + 1);
            trackRepository.save(file);
            return track;
        }
        throw new NotFoundException("Track not found");

    }

    public List<TrackInfoDTO> getAllTracksByUser(int userId) {
        User user = getUserById(userId);
        List<Track> tracks = trackRepository.findAllByOwner(user);
        return tracks.stream()
                .map(t -> mapper.map(t, TrackInfoDTO.class))
                .collect(Collectors.toList());

    }

    public List<TrackInfoDTO> searchTracksByTitle(String title) {

        List<Track> tracks = trackRepository.findAllByTitleContainingIgnoreCase(title);
        return tracks.stream()
                .map(t -> mapper.map(t, TrackInfoDTO.class))
                .collect(Collectors.toList());
    }

    public Page<TrackInfoDTO> getAllPublicTracksWithPagination(int pageNumber) {
        int pageSize = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "uploadedAt"));
        Page<Track> tracksPage = trackRepository.findByIsPublicTrue(pageable);

        return tracksPage.map(track -> mapper.map(track, TrackInfoDTO.class));
    }

}
