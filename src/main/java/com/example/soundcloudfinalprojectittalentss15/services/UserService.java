package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.RegisterDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class UserService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserWithoutPasswordDTO register(@Valid @ModelAttribute("registerDTO") RegisterDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Wrong credentials. Please try again!");
        }

        User u = mapper.map(dto, User.class);
        u.setPassword(encoder.encode(u.getPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
}
