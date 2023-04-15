package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Comment;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService extends AbstractService{


    public List<CommentInfoDTO> getAllByTrack(int trackId) {
        isExistingTrack(trackId);
        List<Comment> comments = commentRepository.findByTrackId(trackId);
        return comments.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    private CommentInfoDTO convertToDto(Comment comment) {
        return mapper.map(comment, CommentInfoDTO.class);
    }

    public boolean isExistingTrack(int trackId) {
        Optional<Track> track = trackRepository.findById(trackId);
        if(track.isEmpty()) {
            throw new BadRequestException("Incorrect track id");
        }
        return true;
    }

    public CommentInfoDTO createComment(int trackId, String content) {
        isExistingTrack(trackId);
        //TODO
        return null;
    }
}
