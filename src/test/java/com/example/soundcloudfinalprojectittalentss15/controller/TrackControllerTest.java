package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.tagDTO.TagSearchDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackEditInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackLikeDTO;

import com.example.soundcloudfinalprojectittalentss15.services.TrackService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest extends AbstractTest {

    @Mock
    private TrackService trackService;
    @InjectMocks
    private TrackController trackController;


    @Test
    void editTrack() {
        TrackEditInfoDTO trackEditInfoDTO = new TrackEditInfoDTO();
        trackEditInfoDTO.setTitle(TRACK_TITLE);
        trackEditInfoDTO.setDescription(TRACK_DESCRIPTION);

        TrackInfoDTO trackInfoDTO = new TrackInfoDTO();
        trackInfoDTO.setId(TRACK_ID);
        trackInfoDTO.setTitle(trackEditInfoDTO.getTitle());
        trackInfoDTO.setDescription(trackEditInfoDTO.getDescription());


        when(trackService.editTrack(TRACK_ID, trackEditInfoDTO, 1)).thenReturn(trackInfoDTO);


        HttpSession mockedSession = mock(HttpSession.class);
        when(mockedSession.getAttribute(LOGGED_ID)).thenReturn(USER_ID);
        TrackInfoDTO result = trackController.editTrack(TRACK_ID, trackEditInfoDTO, mockedSession);

        assertEquals(trackInfoDTO.getId(), result.getId());
        assertEquals(trackInfoDTO.getTitle(), result.getTitle());
        assertEquals(trackInfoDTO.getDescription(), result.getDescription());

        verify(trackService, times(1)).editTrack(TRACK_ID, trackEditInfoDTO, 1);
    }

    @Test
    void likeTrack() {
        TrackLikeDTO trackLikeDTO = new TrackLikeDTO();
        trackLikeDTO.setLiked(true);

        when(trackService.likeTrack(TRACK_ID, USER_ID)).thenReturn(trackLikeDTO);

        HttpSession mockedSession = mock(HttpSession.class);
        when(mockedSession.getAttribute(LOGGED_ID)).thenReturn(USER_ID);
        TrackLikeDTO result = trackController.likeTrack(TRACK_ID, mockedSession);


        assertEquals(trackLikeDTO.isLiked(), result.isLiked());
        verify(trackService, times(1)).likeTrack(TRACK_ID, USER_ID);
    }

    @Test
    void getTrackById() {
        TrackInfoDTO trackInfoDTO = createTrackInfoDTO();

        when(trackService.showTrackById(TRACK_ID)).thenReturn(trackInfoDTO);

        TrackInfoDTO result = trackController.getTrackById(TRACK_ID);

        assertEquals(trackInfoDTO.getId(), result.getId());
        assertEquals(trackInfoDTO.getTitle(), result.getTitle());
        assertEquals(trackInfoDTO.getDescription(), result.getDescription());

        verify(trackService, times(1)).showTrackById(TRACK_ID);
    }

    @Test
    void getAllTracksByUser() {
        TrackInfoDTO trackInfoDTO = createTrackInfoDTO();
        List<TrackInfoDTO> trackList = List.of(trackInfoDTO);


        when(trackService.getAllTracksByUser(USER_ID)).thenReturn(trackList);


        List<TrackInfoDTO> result = trackController.getAllTracksByUser(USER_ID);


        assertEquals(trackList.size(), result.size());

        assertEquals(trackList.get(0).getId(), result.get(0).getId());
        assertEquals(trackList.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(trackList.get(0).getDescription(), result.get(0).getDescription());

        verify(trackService, times(1)).getAllTracksByUser(USER_ID);
    }

    @Test
    void searchTracksByTitle() {
        String title = TRACK_TITLE;
        TrackInfoDTO trackInfoDTO = createTrackInfoDTO();
        List<TrackInfoDTO> trackList = List.of(trackInfoDTO);

        when(trackService.searchTracksByTitle(title)).thenReturn(trackList);

        List<TrackInfoDTO> result = trackController.searchTracksByTitle(title);


        assertEquals(trackList.size(), result.size());

        assertEquals(trackList.get(0).getId(), result.get(0).getId());
        assertEquals(trackList.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(trackList.get(0).getDescription(), result.get(0).getDescription());

        verify(trackService, times(1)).searchTracksByTitle(title);
    }

    @Test
    void getAllTracksWithPagination() {
        TrackInfoDTO trackInfoDTO = createTrackInfoDTO();
        List<TrackInfoDTO> trackList = List.of(trackInfoDTO);
        Page<TrackInfoDTO> trackPage = new PageImpl<>(trackList);

        when(trackService.getAllTracksWithPagination(PAGE_NUMBER)).thenReturn(trackPage);


        Page<TrackInfoDTO> result = trackController.getAllTracksWithPagination(PAGE_NUMBER);


        assertEquals(trackPage.getTotalElements(), result.getTotalElements());

        assertEquals(trackPage.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertEquals(trackPage.getContent().get(0).getTitle(), result.getContent().get(0).getTitle());
        assertEquals(trackPage.getContent().get(0).getDescription(), result.getContent().get(0).getDescription());


        verify(trackService, times(1)).getAllTracksWithPagination(PAGE_NUMBER);
    }

    @Test
    void searchTracksByTags() {
        TagSearchDTO tagSearchDTO = new TagSearchDTO();
        tagSearchDTO.setTags(Collections.singletonList("tag1"));

        List<TrackInfoDTO> trackList = Collections.singletonList(new TrackInfoDTO());
        Page<TrackInfoDTO> trackPage = new PageImpl<>(trackList);


        when(trackService.searchTracksByTags(tagSearchDTO)).thenReturn(trackPage);


        Page<TrackInfoDTO> result = trackController.searchTracksByTags(tagSearchDTO);

        assertEquals(trackPage.getTotalElements(), result.getTotalElements());


        assertEquals(trackPage.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertEquals(trackPage.getContent().get(0).getTitle(), result.getContent().get(0).getTitle());
        assertEquals(trackPage.getContent().get(0).getDescription(), result.getContent().get(0).getDescription());

        verify(trackService, times(1)).searchTracksByTags(tagSearchDTO);
    }



    public TrackInfoDTO createTrackInfoDTO() {
        TrackInfoDTO trackInfoDTO = new TrackInfoDTO();
        trackInfoDTO.setId(TRACK_ID);
        trackInfoDTO.setTitle(TRACK_TITLE);
        trackInfoDTO.setDescription(TRACK_DESCRIPTION);
        return trackInfoDTO;

    }





}
