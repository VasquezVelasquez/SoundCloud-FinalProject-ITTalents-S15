package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Comment;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends AbstractService{


    public List<CommentInfoDTO> getAllByTrack(int trackId) {
        getTrackById(trackId);
        List<Comment> comments = commentRepository.findByTrackId(trackId);
        return comments.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    private CommentInfoDTO convertToDto(Comment comment) {
        return mapper.map(comment, CommentInfoDTO.class);
    }


    public CommentInfoDTO createComment(int trackId, String content, int userId) {
        Track track = getTrackById(trackId);
        User user = getUserById(userId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setTrack(track);
        comment.setPostedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return mapper.map(comment, CommentInfoDTO.class);
    }
}
