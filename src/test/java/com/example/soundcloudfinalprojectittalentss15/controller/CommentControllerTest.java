package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CreationCommentDTO;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import com.example.soundcloudfinalprojectittalentss15.services.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.PageRequest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;




@ExtendWith(MockitoExtension.class)
public class CommentControllerTest extends AbstractTest {

    @Mock
    private CommentService commentService;

    @Mock
    JwtService jwtService;
    @InjectMocks
    private CommentController commentController;


    @Test
    public void getSongComments() {

        CommentInfoDTO commentInfoDTO = createCommentInfoDTO();
        List<CommentInfoDTO> commentList = List.of(commentInfoDTO);
        Page<CommentInfoDTO> comments = new PageImpl<>(commentList, PageRequest.of(PAGE_NUMBER, 10), 1);


        when(commentService.getAllByTrack(TRACK_ID, PAGE_NUMBER)).thenReturn(comments);

        Page<CommentInfoDTO> result = commentController.getSongComments(TRACK_ID, PAGE_NUMBER);

        assertEquals(commentList.size(), result.getContent().size());


        assertEquals(commentList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(commentList.get(0).getContent(), result.getContent().get(0).getContent());
        assertEquals(commentList.get(0).getUserId(), result.getContent().get(0).getUserId());


        verify(commentService, times(1)).getAllByTrack(TRACK_ID, PAGE_NUMBER);
    }







    @Test
    public void getAllCommentsByUser() {
        CommentInfoDTO commentInfoDTO = createCommentInfoDTO();
        List<CommentInfoDTO> commentList = List.of(commentInfoDTO);

        when(commentService.getAllByUser(USER_ID)).thenReturn(commentList);

        List<CommentInfoDTO> result = commentController.getAllCommentsByUser(USER_ID);

        assertEquals(commentList.size(), result.size());
        assertEquals(commentList.get(0).getId(), result.get(0).getId());
        assertEquals(commentList.get(0).getContent(), result.get(0).getContent());
        assertEquals(commentList.get(0).getUserId(), result.get(0).getUserId());

        verify(commentService, times(1)).getAllByUser(USER_ID);
    }


    @Test
    public void deleteCommentTest() {
        CommentInfoDTO comment = createCommentInfoDTO();

        String authHeader = "Bearer testToken";

        when(jwtService.getUserIdFromToken(anyString())).thenReturn(USER_ID);
        when(commentService.deleteComment(TRACK_ID, COMMENT_ID, USER_ID)).thenReturn(comment);


        CommentInfoDTO result = commentController.deleteComment(TRACK_ID, COMMENT_ID, authHeader);

        assertEquals(COMMENT_ID, result.getId());
        assertEquals(COMMENT_CONTENT, result.getContent());
        assertEquals(USER_ID, result.getUserId());

        verify(commentService, times(1)).deleteComment(TRACK_ID, COMMENT_ID, USER_ID);
        verify(jwtService, times(1)).getUserIdFromToken(anyString());
    }


    @Test
    public void createComment() {

        CreationCommentDTO creationCommentDTO = new CreationCommentDTO();
        creationCommentDTO.setContent(COMMENT_CONTENT);

        CommentInfoDTO comment = createCommentInfoDTO();

        String authHeader = "Bearer testToken";

        when(jwtService.getUserIdFromToken(anyString())).thenReturn(USER_ID);
        when(commentService.createComment(TRACK_ID, COMMENT_CONTENT, USER_ID)).thenReturn(comment);

        CommentInfoDTO result = commentController.createComment(TRACK_ID, creationCommentDTO, authHeader);

        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getContent(), result.getContent());
        assertEquals(comment.getUserId(), result.getUserId());

        verify(commentService, times(1)).createComment(TRACK_ID, COMMENT_CONTENT, USER_ID);
        verify(jwtService, times(1)).getUserIdFromToken(anyString());
    }



    @Test
    public void replyToComment() {

        CreationCommentDTO creationCommentDTO = new CreationCommentDTO();
        creationCommentDTO.setContent(COMMENT_CONTENT);

        CommentInfoDTO comment = createCommentInfoDTO();

        String authHeader = "Bearer testToken";
        when(jwtService.getUserIdFromToken(anyString())).thenReturn(USER_ID);
        when(commentService.createReply(TRACK_ID, COMMENT_ID, COMMENT_CONTENT, USER_ID)).thenReturn(comment);

        CommentInfoDTO result = commentController.replyToComment(TRACK_ID, COMMENT_ID, creationCommentDTO, authHeader);

        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getContent(), result.getContent());
        assertEquals(comment.getUserId(), result.getUserId());

        verify(commentService, times(1)).createReply(TRACK_ID, COMMENT_ID, COMMENT_CONTENT, USER_ID);
    }

    private CommentInfoDTO createCommentInfoDTO() {
        CommentInfoDTO commentInfoDTO = new CommentInfoDTO();
        commentInfoDTO.setId(COMMENT_ID);
        commentInfoDTO.setContent(COMMENT_CONTENT);
        commentInfoDTO.setUserId(USER_ID);
        return commentInfoDTO;

    }



}
