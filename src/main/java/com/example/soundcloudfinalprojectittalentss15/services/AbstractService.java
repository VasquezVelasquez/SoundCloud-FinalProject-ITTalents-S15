package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.NotFoundException;
import com.example.soundcloudfinalprojectittalentss15.model.repositories.CommentRepository;
import com.example.soundcloudfinalprojectittalentss15.model.repositories.TrackRepository;
import com.example.soundcloudfinalprojectittalentss15.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TrackRepository trackRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected CommentRepository commentRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected Track getTrackById(int id){
        return trackRepository.findById(id).orElseThrow(() -> new NotFoundException("Track not found"));
    }

//    protected User getUserById(int id){
//        return userRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException("User not found"));
//    }
}
