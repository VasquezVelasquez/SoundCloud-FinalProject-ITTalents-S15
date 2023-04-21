package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.*;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailSenderService emailService;

    @Transactional
    public UserWithoutPasswordDTO register(RegisterDTO dto, String siteURL) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists.");
        }
        if (!dto.getPassword().equals(dto.getConfirmedPassword())) {
            throw new BadRequestException("Passwords mismatch.");
        }
        User user = mapper.map(dto, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encoder.encode(user.getPassword()));

        emailService.sendVerificationEmail(user, siteURL);
        String verificationCode = RandomString.make(64);
        user.setVerificationCode(verificationCode);
        user.setBlocked(false);
        user.setAttempts(0);

        userRepository.save(user);
        return mapper.map(user, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO getUserInfo(int id) {
        User user = getUserById(id);
        return mapper.map(user, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO login(LoginDTO dto, String siteURL) {
        User user = getUserByEmail(dto.getEmail());
        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            handleFailedLogin(user, siteURL);
            throw new UnauthorizedException("Wrong credentials.");
        }

        if (user.isBlocked()) {
            throw new UnauthorizedException("User account is blocked. Please reset your password.");
        }

        return successfulLogin(user);
    }

    void handleFailedLogin(User user, String siteURL) {
        user.setAttempts(user.getAttempts() + 1);

        if (user.getAttempts() >= 3) {
            user.setBlocked(true);
            String resetCode = RandomString.make(64);
            user.setResetCode(resetCode);
            userRepository.save(user);
            emailService.sendResetPasswordEmail(user, siteURL);
            throw new UnauthorizedException("User account is blocked. A reset password email has been sent.");
        }

        userRepository.save(user);
    }

    UserWithoutPasswordDTO successfulLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        user.setAttempts(0);
        userRepository.save(user);
        return mapper.map(user, UserWithoutPasswordDTO.class);
    }


    @Transactional
    public void deleteAccount(PasswordDTO dto, int userId) {
        User user = getUserById(userId);
        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials.");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserWithoutPasswordDTO edit(EditDTO dto, int userId) {
        User user = getUserById(userId);
        user.setDisplayName(dto.getDisplayName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        user.setBio(dto.getBio());
        userRepository.save(user);
        return mapper.map(user, UserWithoutPasswordDTO.class);
    }

    @Transactional
    public void changePassword(ChangePasswordDTO dto, int userId) {
        User user = getUserById(userId);
        if (!encoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials.");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("New password mismatch");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public FollowDTO follow(int followerId, int followedId) {
        if (followerId == followedId) {
            throw new BadRequestException("Cannot follow yourself.");
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

    public String verifyAccount(String code) {
        Optional<User> optionalUser = userRepository.findByVerificationCode(code);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return "You have been verified successfully";
        } else {
            throw new BadRequestException("You are already verified!");
        }
    }

    public void deleteNonVerifiedUsers() {
        LocalDateTime dateTimeThreshold = LocalDateTime.now().minusMinutes(1);
        List<User> nonVerifiedUsers = userRepository.findNonVerifiedUsersRegisteredBefore(dateTimeThreshold);

        for (User user : nonVerifiedUsers) {
            userRepository.delete(user);
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordDTO dto) {
        User user = getUserByEmail(dto.getEmail());

        if (!user.getResetCode().equals(dto.getResetCode())) {
            throw new BadRequestException("Invalid reset code.");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Passwords mismatch.");
        }

        user.setPassword(encoder.encode(dto.getNewPassword()));
        user.setAttempts(0);
        user.setBlocked(false);
        user.setResetCode(null);
        userRepository.save(user);
    }

    public Page<UserBasicInfoDTO> getFollowers(int pageNumber, int loggedId) {
        int pageSize = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> followersPage = userRepository.findAllFollowers(pageable, loggedId);

        return followersPage.map(follower -> mapper.map(follower, UserBasicInfoDTO.class));
    }

    public Page<UserBasicInfoDTO> getFollowedUsers(int pageNumber, int loggedId) {
        int pageSize = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> followedUsersPage = userRepository.findAllFollowedUsers(pageable, loggedId);

        return followedUsersPage.map(followed -> mapper.map(followed, UserBasicInfoDTO.class));
    }


}
