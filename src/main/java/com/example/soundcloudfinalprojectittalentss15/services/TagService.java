package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagPlaylistRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagTrackRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Tag;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;

import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TagService extends AbstractService {


    public TrackInfoDTO addTagsToTrack(TagTrackRequestDTO request, int userId) {
        Track track = getTrackById(request.getTrackId());
        if(track.getOwner().getId() != userId) {
            throw new UnauthorizedException("Action not allowed! You're not the owner of the track! ");
        }
        Set<Tag> currentTags = track.getTags();

        for (TagDTO tagDTO : request.getTags()) {
            Tag tag = tagRepository.findByName(tagDTO.getName());
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagDTO.getName());
                tagRepository.save(tag);
            }
            currentTags.add(tag);
        }

        track.setTags(currentTags);
        trackRepository.save(track);
        return mapper.map(track, TrackInfoDTO.class);
    }

    public PlaylistDTO addTagsToPlaylist(TagPlaylistRequestDTO request, int userId) {
        Playlist playlist = getPlaylistById(request.getPlaylistId());
        if(playlist.getOwner().getId() != userId) {
            throw new UnauthorizedException("Action not allowed! You're not the owner of the playlist!");
        }
        Set<Tag> currentTags = playlist.getTags();

        for (TagDTO tagDTO : request.getTags()) {
            Tag tag = tagRepository.findByName(tagDTO.getName());
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagDTO.getName());
                tagRepository.save(tag);
            }
            currentTags.add(tag);
        }

        playlist.setTags(currentTags);
        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }
}
