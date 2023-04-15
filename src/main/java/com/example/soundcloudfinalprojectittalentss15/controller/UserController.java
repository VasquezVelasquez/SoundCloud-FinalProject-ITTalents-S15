package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.*;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @GetMapping("/users/{id}")
    public UserWithoutPasswordDTO getUserInfo(@PathVariable int id) {
        return userService.getUserInfo(id);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpSession s) {
        if (s.getAttribute("LOGGED_ID") != null && (Boolean)s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is already logged in.");
        }

        UserWithoutPasswordDTO responseDTO = userService.login(dto);

        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", responseDTO.getId());
        return responseDTO;
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpSession s) {
        if (s.getAttribute("LOGGED") != null && (Boolean) s.getAttribute("LOGGED")) {
            s.invalidate();
            return ResponseEntity.ok("User logged out successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not logged in.");
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAccount(@RequestBody PasswordDTO dto, HttpSession s) {
        if (s.getAttribute("LOGGED_ID") != null && (Boolean) s.getAttribute("LOGGED")) {
            userService.deleteAccount(dto, (int) s.getAttribute("LOGGED_ID"));
            s.invalidate();
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not logged in.");
        }
    }

    @PutMapping("/users")
    public UserWithoutPasswordDTO edit(@RequestBody EditDTO dto, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }

        return userService.edit(dto, getLoggedId(s));
    }
//
//    @PostMapping("/users/edit/profile-pic")
//    public UserWithoutPasswordDTO uploadProfilePic(@RequestBody MediaDTO dto) {
//        return null;
//    }
//
//    @PostMapping("/users/edit/background-pic")
//    public UserWithoutPasswordDTO uploadProfilePic(@RequestBody MediaDTO dto) {
//        return null;
//    }
//
//    @PostMapping("/users/{id}/follow")
//    public UserWithoutPasswordDTO follow(@PathVariable int id) {
//        return null;
//    }
//
    @PostMapping("/users/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto, HttpSession s) {
        if (s.getAttribute("LOGGED") == null && !(boolean) s.getAttribute("LOGGED")) {
            throw new BadRequestException("User is not logged in.");
        }

        userService.changePassword(dto, getLoggedId(s));
        return ResponseEntity.ok("Password changed successful.");
    }


}

