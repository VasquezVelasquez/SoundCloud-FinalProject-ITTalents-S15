package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Comment;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends AbstractService{


    public List<CommentInfoDTO> getAllByTrack(int trackId) {
        Optional<Track> track = trackRepository.findById(trackId);
        if(track.isEmpty()) {
            throw new BadRequestException("Incorrect track id");
        }
        List<Comment> comments = null; // TODO
        return null;
    }
}
