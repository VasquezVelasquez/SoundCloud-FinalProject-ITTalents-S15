package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CreationCommentDTO;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommentService commentService;
    @MockBean
    CommentController commentController;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void getSongComments() {
        int trackId = 1;
        int pageNumber = 0;

        // Creating a read-only list with just one object for testing purposes
        List<CommentInfoDTO> commentList = Collections.singletonList(new CommentInfoDTO());

        // Creating a Page object for CommentInfoDTO with the specified list of comments and page number
        Page<CommentInfoDTO> comments = new PageImpl<>(commentList, PageRequest.of(pageNumber, 10), 1);

        // Mocking the getAllByTrack method of the commentService
        when(commentService.getAllByTrack(trackId, pageNumber)).thenReturn(comments);

        // Calling the getSongComments method of the commentController and passing in the required arguments
        Page<CommentInfoDTO> result = commentController.getSongComments(trackId, pageNumber);

        // Asserting that the size of the returned list matches the expected size
        assertEquals(commentList.size(), result.getContent().size());

        // Asserting that the values of the properties of the returned CommentInfoDTO match the expected values
        assertEquals(commentList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(commentList.get(0).getContent(), result.getContent().get(0).getContent());
        assertEquals(commentList.get(0).getUserId(), result.getContent().get(0).getUserId());

        // Verifying that the getAllByTrack method of the commentService was called once with the expected arguments
        verify(commentService, times(1)).getAllByTrack(trackId, pageNumber);
    }



    @Test
    void getAllCommentsByUser() {
        int userId = 1;
        CommentInfoDTO comment = new CommentInfoDTO();
        comment.setId(1);
        comment.setContent("Best track!");
        comment.setUserId(userId);
        List<CommentInfoDTO> commentList = List.of(comment);

        when(commentService.getAllByUser(userId)).thenReturn(commentList);

        List<CommentInfoDTO> result = commentController.getAllCommentsByUser(userId, mock(HttpSession.class));

        assertEquals(commentList.size(), result.size());

        assertEquals(commentList.get(0).getId(), result.get(0).getId());
        assertEquals(commentList.get(0).getContent(), result.get(0).getContent());
        assertEquals(commentList.get(0).getUserId(), result.get(0).getUserId());

        verify(commentService, times(1)).getAllByUser(userId);
    }


    @Test
    void deleteCommentTest() {
        int trackId = 1;
        int commentId = 1;
        int userId = 1;

        CommentInfoDTO comment = new CommentInfoDTO();
        comment.setId(commentId);
        comment.setContent("Best track!");
        comment.setUserId(userId);

        when(commentService.deleteComment(trackId, commentId, userId)).thenReturn(comment);

        CommentInfoDTO result = commentController.deleteComment(trackId, commentId, mock(HttpSession.class));

        assertEquals(commentId, result.getId());
        assertEquals("Best track!", result.getContent());
        assertEquals(userId, result.getUserId());

        verify(commentService, times(1)).deleteComment(trackId, commentId, userId);
    }

    @Test
    void createComment() {
        int trackId = 1;
        int userId = 1;
        String content = "Great track!";

        CreationCommentDTO creationCommentDTO = new CreationCommentDTO();
        creationCommentDTO.setContent(content);

        CommentInfoDTO comment = new CommentInfoDTO();
        comment.setId(1);
        comment.setContent(content);
        comment.setUserId(userId);

        when(commentService.createComment(trackId, content, userId)).thenReturn(comment);

        CommentInfoDTO result = commentController.createComment(trackId, creationCommentDTO, mock(HttpSession.class));

        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getContent(), result.getContent());
        assertEquals(comment.getUserId(), result.getUserId());

        verify(commentService, times(1)).createComment(trackId, content, userId);
    }

    @Test
    void replyToComment() {
        int trackId = 1;
        int commentId = 1;
        int userId = 1;
        String content = "Thanks for the feedback!";

        CreationCommentDTO creationCommentDTO = new CreationCommentDTO();
        creationCommentDTO.setContent(content);

        CommentInfoDTO comment = new CommentInfoDTO();
        comment.setId(2);
        comment.setContent(content);
        comment.setUserId(userId);

        when(commentService.createReply(trackId, commentId, content, userId)).thenReturn(comment);

        CommentInfoDTO result = commentController.replyToComment(trackId, commentId, creationCommentDTO, mock(HttpSession.class));

        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getContent(), result.getContent());
        assertEquals(comment.getUserId(), result.getUserId());

        verify(commentService, times(1)).createReply(trackId, commentId, content, userId);
    }



}
