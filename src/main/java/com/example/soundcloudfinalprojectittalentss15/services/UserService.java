package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.*;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserWithoutPasswordDTO register(RegisterDTO dto) {

        if (!dto.getPassword().equals(dto.getConfirmedPassword())) {
            throw new BadRequestException("Passwords mismatch");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User u = mapper.map(dto, User.class);
        u.setCreatedAt(LocalDateTime.now());
        u.setPassword(encoder.encode(u.getPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO getUserInfo(int id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            throw new BadRequestException("No such user");
        }

        return mapper.map(opt.get(), UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO login(LoginDTO dto) {
        Optional<User> opt = userRepository.findByEmail(dto.getEmail());
        if (opt.isEmpty()) {
            throw new UnauthorizedException("Wrong credentials.");
        }

        User u = opt.get();

        if (!encoder.matches(dto.getPassword(), u.getPassword())) {
            throw new UnauthorizedException("Wrong credentials.");
        }

        u.setLastLogin(LocalDateTime.now());
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public void deleteAccount(PasswordDTO dto, int loggedId) {
        Optional<User> optionalUser = userRepository.findById(loggedId);

        if (optionalUser.isEmpty()) {
            throw new BadRequestException("User doesn't exist.");
        }

        if (!encoder.matches(dto.getPassword(), optionalUser.get().getPassword())) {
            throw new UnauthorizedException("Wrong credentials.");
        }

        userRepository.deleteById(loggedId);
    }

    public UserWithoutPasswordDTO edit(EditDTO dto, int userId) {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty()) {
            throw new BadRequestException("User doesn't exist.");
        }

        User u = opt.get();

        u.setDisplayName(dto.getDisplayName());
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setAge(dto.getAge());
        u.setGender(dto.getGender());
        u.setBio(dto.getBio());
        userRepository.save(u);

        return mapper.map(u, UserWithoutPasswordDTO.class);
    }


    public void changePassword(ChangePasswordDTO dto, int loggedId) {
        Optional<User> opt = userRepository.findById(loggedId);

        if (opt.isEmpty()) {
            throw new BadRequestException("User doesn't exist.");
        }

        User u = opt.get();

        if (!encoder.matches(dto.getPassword(), u.getPassword())) {
            throw new UnauthorizedException("Wrong credentials.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("New password mismatch");
        }

        u.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(u);
    }

    public FollowDTO follow(int followerId, int followedId) {
        if (followedId == followerId) {
            throw new BadRequestException("Cannot follow yourself");
        }

        boolean isFollowing = true;

        User follower = getUserById(followerId);
        User followed = getUserById(followedId);
        if (followed.getFollowers().contains(follower)) {
            followed.getFollowers().remove(follower);
            follower.getFollowedUsers().remove(followed);
            isFollowing = false;
        } else {
            followed.getFollowers().add(follower);
            follower.getFollowedUsers().add(followed);
        }
        userRepository.save(follower);
        userRepository.save(followed);


        return convertToFollowDTO(follower, followed, isFollowing);
    }

    public List<UserWithoutPasswordDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map( u -> mapper.map(u, UserWithoutPasswordDTO.class))
                .collect(Collectors.toList());
    }

    public FollowDTO convertToFollowDTO(User follower, User followedUser, boolean isFollowing) {

        // Map follower data to FollowDTO
        FollowDTO followDTO = mapper.map(follower, FollowDTO.class);
        followDTO.setFollowerId(follower.getId());
        followDTO.setFollowerDisplayName(follower.getDisplayName());

        // Map followed user data to FollowDTO
        followDTO.setFollowedUserId(followedUser.getId());
        followDTO.setFollowedUserDisplayName(followedUser.getDisplayName());

        // Set isFollowing and followEventAt
        followDTO.setFollowing(isFollowing);
        followDTO.setFollowEventAt(LocalDateTime.now());

        return followDTO;
    }
}
