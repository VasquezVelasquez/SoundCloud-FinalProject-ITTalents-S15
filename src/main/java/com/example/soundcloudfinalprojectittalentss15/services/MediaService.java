package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.playlistDTO.PlaylistDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.NotFoundException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MediaService extends AbstractService{


    @SneakyThrows
    public UserWithoutPasswordDTO uploadProfilePicture(MultipartFile file, int userId) {
        String name = createFileName(file);
        File dir = new File("pictures");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream(), f.toPath());
        String url = dir.getName() + File.separator + f.getName();
        User u = getUserById(userId);
        if (u.getProfilePictureUrl() != null) {
            // Delete the current profile picture
            File currentProfilePicture = new File(u.getProfilePictureUrl());
            if (currentProfilePicture.exists()) {
                currentProfilePicture.delete();
            }
        }

        u.setProfilePictureUrl(url);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }



    @SneakyThrows
    public UserWithoutPasswordDTO uploadBackgroundPicture(MultipartFile file, int userId) {
        String name = createFileName(file);
        File dir = new File("pictures");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream(), f.toPath());
        String url = dir.getName() + File.separator + f.getName();
        User u = getUserById(userId);
        if (u.getBackgroundPictureUrl() != null) {
            File currentProfileBackground = new File(u.getBackgroundPictureUrl());
            if (currentProfileBackground.exists()) {
                currentProfileBackground.delete();
            }
        }

        u.setBackgroundPictureUrl(url);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public File download(String fileName) {
        File dir = new File("pictures");

        File f = new File(dir, fileName);
        System.out.println(f.getName());
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }

    public TrackInfoDTO uploadTrackCoverPicture(MultipartFile file, int trackId, int loggedId) {
        if(!isValidPictureFile(file)) {
            throw new BadRequestException("File type not accepted!");
        }
        Track track = getTrackById(trackId);
        if(track.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Not authorized action!");
        }
        String name = createFileName(file);
        String url = createFile(file, name, "pictures");
        track.setCoverPictureUrl(url);
        trackRepository.save(track);
        return mapper.map(track, TrackInfoDTO.class);
    }








    @SneakyThrows
    public PlaylistDTO uploadPlaylistCoverPicture(MultipartFile file, int playlistId, int loggedId) {
        Playlist playlist = getPlaylistById(playlistId);
        if (playlist.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Not authorized action!");
        }
        String fileName = createFileName(file);
        String url = createFile(file, fileName, "pictures");

        if (playlist.getCoverPictureUrl() != null) {
            Files.deleteIfExists(Paths.get(playlist.getCoverPictureUrl()));
        }

        playlist.setCoverPictureUrl(url);
        playlistRepository.save(playlist);
        return mapper.map(playlist, PlaylistDTO.class);
    }


}
