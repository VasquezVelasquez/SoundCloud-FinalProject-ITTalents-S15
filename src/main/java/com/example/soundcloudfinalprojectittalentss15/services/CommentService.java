package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Comment;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends AbstractService{


    public List<CommentInfoDTO> getAllByTrack(int trackId) {
        getTrackById(trackId);
        List<Comment> comments = commentRepository.findByTrackId(trackId);
        return comments.stream().map(comment -> mapper.map(comment, CommentInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentInfoDTO createComment(int trackId, String content, int userId) {
        if(content.length() >= 500) {
            throw new BadRequestException("Content length exceeded. 500 symbols maximum! ");
        }
        Track track = getTrackById(trackId);
        User user = getUserById(userId);
        Comment comment = fillInCommentData(content, user, track);
        commentRepository.save(comment);
        return mapper.map(comment, CommentInfoDTO.class);
    }


    public List<CommentInfoDTO> getAllByUser(int userId) {
        getUserById(userId);
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream().map(comment -> mapper.map(comment, CommentInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentInfoDTO createReply(int trackId, int commentId, String content, int userId) {
        if(content.length() >= 500) {
            throw new BadRequestException("Content length exceeded. 500 symbols maximum! ");
        }
        Track track = getTrackById(trackId);
        User user = getUserById(userId);
        Comment comment = getCommentById(commentId);
        Comment reply = fillInCommentData(content, user, track);
        reply.setParentComment(comment);
        commentRepository.save(reply);
        return mapper.map(reply, CommentInfoDTO.class);
    }

    @Transactional
    public CommentInfoDTO deleteComment(int trackId, int commentId, int userId) {
        Track track = getTrackById(trackId);
        Comment comment = getCommentById(commentId);
        if(trackId != comment.getTrack().getId()) {
            throw new BadRequestException("Comment not found");
        }
        if((track.getOwner().getId() != userId) && (comment.getUser().getId() != userId)) {
            throw new UnauthorizedException("User cannot access this operation! ");
        }
        CommentInfoDTO commentInfoDTO = mapper.map(comment, CommentInfoDTO.class);
        commentRepository.delete(comment);
        return commentInfoDTO;
    }

    public Comment fillInCommentData(String content, User user, Track track) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setTrack(track);
        comment.setPostedAt(LocalDateTime.now());
        return comment;

    }
}
