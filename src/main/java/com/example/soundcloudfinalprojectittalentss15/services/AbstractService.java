package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Comment;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.NotFoundException;
import com.example.soundcloudfinalprojectittalentss15.model.repositories.*;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
public abstract class AbstractService {

    public static final String PICTURES_DIRECTORY = "pictures";
    public static final String TRACKS_DIRECTORY = "tracks";

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TrackRepository trackRepository;
    @Autowired
    protected PlaylistRepository playlistRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected TagRepository tagRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected Track getTrackById(int id){
        return trackRepository.findById(id).orElseThrow(() -> new NotFoundException("Track not found"));
    }

    protected Playlist getPlaylistById(int id) {
        return playlistRepository.findById(id).orElseThrow(() -> new NotFoundException("Playlist not found"));
    }

    protected Comment getCommentById(int id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
    }

    @SneakyThrows
    protected String createFile(MultipartFile file, String name, String folderName) {
        File dir = new File(folderName);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream() , f.toPath());
        return dir.getName() + File.separator + f.getName();

    }

    protected String createFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return UUID.randomUUID() + "." + extension;
    }

    public boolean isValidAudioFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equalsIgnoreCase("audio/mpeg");
    }
    public boolean isValidPictureFile(MultipartFile file) {
        String contentType = file.getContentType();
        boolean isValidImage = contentType != null &&
                (contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/png"));
        return isValidImage;
    }

}
