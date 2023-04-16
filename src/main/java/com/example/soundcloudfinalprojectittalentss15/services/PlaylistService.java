package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.CreateTrackDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService extends AbstractService{


    public PlaylistDTO create(CreateTrackDTO dto, int loggedId) {
        Playlist playlist = mapper.map(dto, Playlist.class);
        playlist.setOwner(getUserById(loggedId));
        playlist.setPublic(dto.isPublic());
        playlist.setCreatedAt(LocalDateTime.now());

        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }

    public PlaylistDTO addTrack(int playlistId, int trackId, int loggedId) {
        return null;
    }

    public ResponseEntity<String> deletePlaylist(int playlistId, int loggedId) {
        return null;
    }

    public List<PlaylistDTO> getPlaylistsByUserId(int id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User doesn't exist.");
        }

        List<Playlist> playlists = playlistRepository.findByOwnerId(id);
        return playlists.stream()
                .map(playlist -> mapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
    }
}
