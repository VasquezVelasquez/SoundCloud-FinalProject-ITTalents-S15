package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)

public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @Test
    void getSongComments() throws Exception {
        int trackId = 1;
        int pageNumber = 0;
        List<CommentInfoDTO> commentList = Collections.singletonList(new CommentInfoDTO());
        Page<CommentInfoDTO> comments = new PageImpl<>(commentList);

        when(commentService.getAllByTrack(trackId, pageNumber)).thenReturn(comments);

        mockMvc.perform(get("/track/{trackId}/comments", trackId)
                        .param("page", String.valueOf(pageNumber)))
                .andExpect(status().isOk());

        verify(commentService).getAllByTrack(trackId, pageNumber);
    }
}
