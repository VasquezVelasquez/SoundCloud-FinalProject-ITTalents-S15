package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.*;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
}
