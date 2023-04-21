package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.*;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto, HttpServletRequest request) {
        return userService.register(dto, getRequestSiteURL(request));
    }

    @GetMapping("/users/{id}")
    public UserWithoutPasswordDTO getUserInfo(@PathVariable int id) {
        return userService.getUserInfo(id);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpSession s, HttpServletRequest request) {
        if (isLogged(s)) {
            throw new BadRequestException("User is already logged in.");
        }

        UserWithoutPasswordDTO responseDTO = userService.login(dto, getRequestSiteURL(request));
        s.setAttribute(LOGGED_ID, responseDTO.getId());
        return responseDTO;
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpSession s) {
        s.invalidate();
        return ResponseEntity.ok("User logged out successfully.");
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAccount(@RequestBody PasswordDTO dto, HttpSession s) {
        if (isLogged(s)) {
            userService.deleteAccount(dto, (int) s.getAttribute(LOGGED_ID));
            s.invalidate();
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not logged in.");
        }
    }

    @PutMapping("/users")
    public UserWithoutPasswordDTO edit(@Valid @RequestBody EditDTO dto, HttpSession s) {
        if (!isLogged(s)) {
            throw new BadRequestException("User is not logged in.");
        }
        return userService.edit(dto, getLoggedId(s));
    }

    @PostMapping("/users/{followedId}/follow")
    public FollowDTO follow(@PathVariable int followedId, HttpSession s) {
        int followerId = getLoggedId(s);
        return userService.follow(followerId, followedId);
    }

    @PostMapping("/users/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO dto, HttpSession s) {
        userService.changePassword(dto, getLoggedId(s));
        return ResponseEntity.ok("Password changed successful.");
    }

    @PostMapping("/reset-password")
    //todo check if new password matches the old one
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        userService.resetPassword(dto);
        return ResponseEntity.ok().body("Password reset successfully");
    }

    @GetMapping("/users/all")
    public List<UserWithoutPasswordDTO> getAll(){
        return userService.getAll();
    }

//    @GetMapping("/users/followers")
//    public List<UserWithoutPasswordDTO> getFollowers(HttpSession s) {
//        return userService.getFollowers(getLoggedId(s));
//    }

    @GetMapping("/users/followers")
    public Page<UserBasicInfoDTO> getFollowersWithPagination(@RequestParam(name = "page", defaultValue = "0") int pageNumber, HttpSession s) {
        return userService.getFollowers(pageNumber, getLoggedId(s));
    }

    @GetMapping("/users/followed")
    public Page<UserBasicInfoDTO> getFollowed(@RequestParam(name = "page", defaultValue = "0") int pageNumber, HttpSession s) {
        return userService.getFollowedUsers(pageNumber, getLoggedId(s));
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code){
        return userService.verifyAccount(code);
    }
}

