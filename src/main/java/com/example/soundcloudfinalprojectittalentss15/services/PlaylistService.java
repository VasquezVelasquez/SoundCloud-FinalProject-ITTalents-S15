package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.CreatePlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.EditPlaylistInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.NotFoundException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import com.example.soundcloudfinalprojectittalentss15.model.repositories.PlaylistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService extends AbstractService{


    public PlaylistDTO likePlaylist(int id, int userId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (optionalPlaylist.isEmpty()) {
            throw new NotFoundException("Playlist not found");
        }
        Playlist p = optionalPlaylist.get();
        User u = getUserById(userId);

        if (u.getLikedPlaylists().contains(p)) {
            u.getLikedPlaylists().remove(p);
        } else {
            u.getLikedPlaylists().add(p);
        }
        userRepository.save(u);

        return mapper.map(p, PlaylistDTO.class);
    }

    public PlaylistDTO create(CreatePlaylistDTO dto, int loggedId) {
        int trackId = dto.getTrackId();
        Optional<Track> optTrack = trackRepository.getById(trackId);
        if (optTrack.isEmpty()) {
            throw new NotFoundException("Track not found");
        }
        Track t = optTrack.get();

        if (playlistRepository.existsByTitle(dto.getTitle())) {
            throw new BadRequestException("Playlist with this title already exists");
        }

        Playlist playlist = mapper.map(dto, Playlist.class);

        playlist.setTitle(dto.getTitle());
        playlist.setOwner(getUserById(loggedId));
        playlist.setPublic(dto.isPublic());
        playlist.setCreatedAt(LocalDateTime.now());

        playlist.getTracks().add(t);
        t.getPlaylists().add(playlist);
        playlistRepository.save(playlist);


        return mapper.map(playlist, PlaylistDTO.class);
    }

    public PlaylistDTO addTrack(int playlistId, int trackId, int loggedId) {
        Optional<Playlist> opt = playlistRepository.findById(playlistId);
        if (opt.isEmpty()) {
            throw new NotFoundException("Playlist not found");
        }

        Playlist p = opt.get();
        if (p.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Unauthorized action");
        }

        Optional<Track> optTrack = trackRepository.findById(trackId);
        if (optTrack.isEmpty()) {
            throw new NotFoundException("Track not found");
        }

        Track t = optTrack.get();
        p.getTracks().add(t);
        t.getPlaylists().add(p);
        trackRepository.save(t);
        playlistRepository.save(p);
        return mapper.map(p, PlaylistDTO.class);
    }

    public ResponseEntity<String> deletePlaylist(int playlistId, int loggedId) {
        Optional<Playlist> opt = playlistRepository.getPlaylistsById(playlistId);

        if (opt.isEmpty()) {
            throw new NotFoundException("Playlist not found");
        }

        Playlist p = opt.get();

        if (loggedId != p.getOwner().getId()) {
            throw new UnauthorizedException("Unauthorized action");
        }

        playlistRepository.delete(p);
        return ResponseEntity.ok("Playlist with " + p.getTitle() + " was deleted successfully.");
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


    public PlaylistDTO editInfo(int playlistId, EditPlaylistInfoDTO dto, int loggedId) {
        Optional<Playlist> opt = playlistRepository.getPlaylistsById(playlistId);
        if (opt.isEmpty()) {
            throw new NotFoundException("Playlist not found.");
        }

        Playlist p = opt.get();

        if (loggedId != p.getOwner().getId()) {
            throw new UnauthorizedException("Unauthorized action.");
        }

        p.setTitle(dto.getTitle());
        p.setPublic(dto.isPublic());
        p.setDescription(dto.getDescription());

        playlistRepository.save(p);
        return mapper.map(p, PlaylistDTO.class);
    }


    public List<PlaylistDTO> getPlaylistsByName(String name) {
        List<Playlist> playlists = playlistRepository.getAllByTitleContaining(name);
        return playlists.stream()
                .map(playlist -> mapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
    }

    public PlaylistDTO removeTrackById(int playlistId, int trackId, int loggedId) {
        Optional<Playlist> opt = playlistRepository.getPlaylistsById(playlistId);
        if (opt.isEmpty()) {
            throw new NotFoundException("Playlist not found");
        }

        Playlist p = opt.get();
        if (p.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Unauthorized action");
        }

        Optional<Track> optTrack = trackRepository.getById(trackId);
        if (optTrack.isEmpty()) {
            throw new NotFoundException("Track not found");
        }

        playlistRepository.removeTrackFromPlaylist(playlistId, trackId);
        playlistRepository.save(p);

        return mapper.map(p, PlaylistDTO.class);
    }
}
