package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CreationCommentDTO;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController extends AbstractController{


    private final CommentService commentService;

    @GetMapping("/track/{trackId}/comments")
    public Page<CommentInfoDTO> getSongComments(@PathVariable int trackId, @RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        return commentService.getAllByTrack(trackId, pageNumber);

    }

    @PostMapping("/track/{trackId}/comments")
    public CommentInfoDTO createComment(@PathVariable int trackId, @RequestBody CreationCommentDTO creationCommentDTO, @RequestHeader("Authorization") String authHeader) {
        return commentService.createComment(trackId, creationCommentDTO.getContent(), getUserIdFromHeader(authHeader));
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentInfoDTO> getAllCommentsByUser(@PathVariable int userId) {
        return commentService.getAllByUser(userId);
    }

    @PostMapping("/track/{trackId}/comments/{commentId}")
    public CommentInfoDTO replyToComment(@PathVariable int trackId, @PathVariable int commentId, @RequestBody CreationCommentDTO creationCommentDTO, @RequestHeader("Authorization") String authHeader) {
        int userId = getUserIdFromHeader(authHeader);
        return commentService.createReply(trackId, commentId, creationCommentDTO.getContent(), userId);
    }

    @DeleteMapping("/track/{trackId}/comments/{commentId}")
    public CommentInfoDTO deleteComment(@PathVariable int trackId, @PathVariable int commentId, @RequestHeader("Authorization") String authHeader) {
        int userId = getUserIdFromHeader(authHeader);
        return commentService.deleteComment(trackId, commentId, userId);
    }


}
