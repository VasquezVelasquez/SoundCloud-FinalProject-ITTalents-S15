package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagRequestDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Tag;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;

import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TagService extends AbstractService {


    public TrackInfoDTO addTagsToTrack(TagRequestDTO request, int userId) {
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

}
