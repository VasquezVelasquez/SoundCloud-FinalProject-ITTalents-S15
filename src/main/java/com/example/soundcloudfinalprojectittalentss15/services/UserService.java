package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.LoginDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.RegisterDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        u.setLastLogin(LocalDateTime.now());
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

        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
}
