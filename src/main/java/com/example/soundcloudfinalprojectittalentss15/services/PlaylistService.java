package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.CreatePlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.EditPlaylistInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagSearchDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService extends AbstractService{

    @Transactional
    public PlaylistDTO likePlaylist(int playlistId, int userId) {
        Playlist playlist = getPlaylistById(playlistId);
        User u = getUserById(userId);
        //TODO tell if liked or not
        if (u.getLikedPlaylists().contains(playlist)) {
            u.getLikedPlaylists().remove(playlist);
        } else {
            u.getLikedPlaylists().add(playlist);
        }
        userRepository.save(u);
        return mapper.map(playlist, PlaylistDTO.class);
    }

    @Transactional
    public PlaylistDTO create(CreatePlaylistDTO dto, int userId) {
        Track track = getTrackById(dto.getTrackId());
        //TODO FIX!!!
        Playlist playlist = mapper.map(dto, Playlist.class);
        playlist.setTitle(dto.getTitle());
        playlist.setOwner(getUserById(userId));
        playlist.setPublic(dto.isPublic());
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.getTracks().add(track);
        track.getPlaylists().add(playlist);
        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }

    @Transactional
    public PlaylistDTO addTrack(int playlistId, int trackId, int userId) {
        Playlist playlist = getPlaylistById(playlistId);
        checkOwnership(playlist.getOwner().getId(), userId);
        Track track = getTrackById(trackId);

        playlist.getTracks().add(track);
        track.getPlaylists().add(playlist);
        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }

    @Transactional
    public ResponseEntity<String> deletePlaylist(int playlistId, int userId) {
        Playlist playlist = getPlaylistById(playlistId);
        checkOwnership(playlist.getOwner().getId(), userId);
        playlistRepository.delete(playlist);
        return ResponseEntity.ok("Playlist with " + playlist.getTitle() + " was deleted successfully.");
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

    @Transactional
    public PlaylistDTO editInfo(int playlistId, EditPlaylistInfoDTO dto, int userId) {
        Playlist playlist = getPlaylistById(playlistId);
        checkOwnership(playlist.getOwner().getId(), userId);

        playlist.setTitle(dto.getTitle());
        playlist.setPublic(dto.isPublic());
        playlist.setDescription(dto.getDescription());
        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }


    public List<PlaylistDTO> getPlaylistsByName(String name) {
        List<Playlist> playlists = playlistRepository.getAllByTitleContainingIgnoreCase(name);
        return playlists.stream()
                .map(playlist -> mapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PlaylistDTO removeTrackById(int playlistId, int trackId, int userId) {
        Playlist playlist = getPlaylistById(playlistId);
        checkOwnership(playlist.getOwner().getId(), userId);
        Track track = getTrackById(trackId);

        playlist.getTracks().remove(track);
        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }

    public List<PlaylistDTO> getLikedPlaylists(int id) {
        List<Playlist> playlists = playlistRepository.findAllLikedPlaylistsByUserId(id);
        return playlists.stream()
                .map(playlist -> mapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
    }

    public Page<PlaylistDTO> searchPlaylistsByTags(TagSearchDTO request) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(request.getPage(), pageSize);
        List<String> tags = request.getTags();
        long tagCount = tags.size();
        Page<Playlist> playlistPage = playlistRepository.findByTags(tags, tagCount, pageable);
        return playlistPage.map(p -> mapper.map(p, PlaylistDTO.class));
    }
}
