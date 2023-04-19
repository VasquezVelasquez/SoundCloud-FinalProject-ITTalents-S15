package com.example.soundcloudfinalprojectittalentss15.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;

import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class StorageService extends AbstractService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    @SneakyThrows
    public TrackInfoDTO uploadTrack(MultipartFile trackFile, String title, String description, int loggedId) {
        isValidAudioFile(trackFile);
        String fileName = createFileName(trackFile);
        Path tempFilePath = null;

        try (InputStream inputStream = trackFile.getInputStream()) {
            tempFilePath = Files.createTempFile("temp_", "_" + fileName);
            Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            s3Client.putObject(bucketName, fileName, tempFilePath.toFile());
        } finally {
            if (tempFilePath != null) {
                Files.delete(tempFilePath);
            }
        }

        Track track = new Track();
        track.setTitle(title);
        track.setUploadedAt(LocalDateTime.now());
        track.setTrackUrl(fileName);
        track.setDescription(description);
        track.setOwner(getUserById(loggedId));
        track.setPlays(1);
        trackRepository.save(track);
        return mapper.map(track, TrackInfoDTO.class);
    }

    public byte[] downloadTrack(String fileName) {
        S3Object s3Object =  s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            Track track = trackRepository.findByTrackUrl(fileName);
            track.setPlays(track.getPlays() + 1);
            trackRepository.save(track);
            return content;
        } catch (IOException e) {
            throw new BadRequestException("Track Not Found! ");
        }
    }

    @Transactional
    public TrackInfoDTO deleteTrack(int trackId, int loggedId) {
        Track track = getTrackById(trackId);
        if(track.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Action not allowed! ");
        }
        s3Client.deleteObject(bucketName, track.getTrackUrl());
        TrackInfoDTO trackInfoDTO = mapper.map(track, TrackInfoDTO.class);
        trackRepository.deleteById(trackId);
        return trackInfoDTO;

    }
}
