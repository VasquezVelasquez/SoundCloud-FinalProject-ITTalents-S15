package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.UserWithoutPasswordDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaService extends AbstractService{


    public UserWithoutPasswordDTO uploadUserProfilePicture(MultipartFile file, int loggedId) {
        String ext = Filename
    }
}
