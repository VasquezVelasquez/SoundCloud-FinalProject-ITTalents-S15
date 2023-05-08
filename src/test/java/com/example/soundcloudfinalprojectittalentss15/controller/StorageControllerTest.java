package com.example.soundcloudfinalprojectittalentss15.controller;


import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackUploadInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.services.StorageService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StorageControllerTest extends AbstractTest{

    @Mock
    private StorageService storageService;

    @InjectMocks
    private StorageController storageController;

    @Test
    public void uploadTest() {
        MultipartFile trackFile = new MockMultipartFile(TRACK_TITLE, "test.mp3", "audio/mpeg", "test".getBytes());

        TrackUploadInfoDTO expectedResponse = new TrackUploadInfoDTO();
        expectedResponse.setTitle(TRACK_TITLE);
        expectedResponse.setDescription(TRACK_DESCRIPTION);
        expectedResponse.setId(USER_ID);

        when(storageService.uploadTrack(trackFile, TRACK_TITLE, TRACK_DESCRIPTION, USER_ID)).thenReturn(expectedResponse);

        HttpSession mockedSession = mock(HttpSession.class);
        when(mockedSession.getAttribute(LOGGED_ID)).thenReturn(USER_ID);

        TrackUploadInfoDTO result = storageController.upload(trackFile, TRACK_TITLE, TRACK_DESCRIPTION, mockedSession);

        assertEquals(expectedResponse.getTitle(), result.getTitle());
        assertEquals(expectedResponse.getDescription(), result.getDescription());
        assertEquals(expectedResponse.getId(), result.getId());

        verify(storageService, times(1)).uploadTrack(trackFile, TRACK_TITLE, TRACK_DESCRIPTION, USER_ID);
    }

    @Test
    public void downloadTrackTest() {
        String url = "test-track-url";
        byte[] track = "test".getBytes();

        when(storageService.downloadTrack(url)).thenReturn(track);

        ResponseEntity<ByteArrayResource> response = storageController.downloadTrack(url);

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(response.getHeaders().getContentType().toString(), "audio/mpeg");
        assertEquals(response.getBody().contentLength(), track.length);

        verify(storageService, times(1)).downloadTrack(url);
    }

    @Test
    public void deleteTrackTest() {

        TrackInfoDTO expectedResponse = new TrackInfoDTO();
        expectedResponse.setId(TRACK_ID);

        when(storageService.deleteTrack(TRACK_ID, USER_ID)).thenReturn(expectedResponse);

        HttpSession mockedSession = mock(HttpSession.class);
        when(mockedSession.getAttribute(LOGGED_ID)).thenReturn(USER_ID);

        TrackInfoDTO result = storageController.deleteTrack(TRACK_ID, mockedSession);

        assertEquals(expectedResponse.getId(), result.getId());

        verify(storageService, times(1)).deleteTrack(TRACK_ID, USER_ID);
    }
}
