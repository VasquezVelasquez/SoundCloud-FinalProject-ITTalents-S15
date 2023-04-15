package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Comment;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController extends AbstractController{

    @Autowired
    private CommentService commentService;

    @GetMapping("/track/{trackId}/comments")
    public List<CommentInfoDTO> getSongComments(@PathVariable int trackId) {
        return commentService.getAllByTrack(trackId);

    }

    @PostMapping("/track/{trackId}/comments")
    public CommentInfoDTO createComment(@PathVariable int trackId, @RequestBody String content) {
        return commentService.createComment(trackId, content);
    }


}
