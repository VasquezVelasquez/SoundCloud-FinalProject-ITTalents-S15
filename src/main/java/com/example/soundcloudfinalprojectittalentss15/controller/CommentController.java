package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController extends AbstractController{

    @Autowired
    private CommentService commentService;

    @GetMapping("/track/{trackId}/comments")
    public Page<CommentInfoDTO> getSongComments(@PathVariable int trackId, @RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        return commentService.getAllByTrack(trackId, pageNumber);

    }

    @PostMapping("/track/{trackId}/comments")
    public CommentInfoDTO createComment(@PathVariable int trackId, @RequestBody String content, HttpSession s) {
        int userId = getLoggedId(s);
        return commentService.createComment(trackId, content, userId);
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentInfoDTO> getAllCommentsByUser(@PathVariable int userId, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }
        return commentService.getAllByUser(userId);
    }

    @PostMapping("/track/{trackId}/comments/{commentId}")
    public CommentInfoDTO replyToComment(@PathVariable int trackId, @PathVariable int commentId, @RequestBody String content,  HttpSession s) {
        int userId = getLoggedId(s);
        return commentService.createReply(trackId, commentId, content, userId);
    }

    @DeleteMapping("/track/{trackId}/comments/{commentId}")
    public CommentInfoDTO deleteComment(@PathVariable int trackId, @PathVariable int commentId, HttpSession s) {
        int userId = getLoggedId(s);
        return commentService.deleteComment(trackId, commentId, userId);
    }


}
