package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.config.TokenBlacklist;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.AuthenticationResponse;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.userDTOs.*;
import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController extends AbstractController{


    private final UserService userService;

    private final TokenBlacklist tokenBlacklist;

    @PostMapping("/users/auth")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto, HttpServletRequest request) {
        return userService.register(dto, getRequestSiteURL(request));
    }

    @GetMapping("/users/{id}")
    public UserWithoutPasswordDTO getUserInfo(@PathVariable int id) {
        return userService.getUserInfo(id);
    }

    @PostMapping("/users/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        var authenticationResponse = userService.login(dto, getRequestSiteURL(request));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authenticationResponse.getToken());
        return ResponseEntity.ok().headers(httpHeaders).body(authenticationResponse);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        final String jwt = getTokenFromHeader(authHeader);
        tokenBlacklist.add(jwt);
        return ResponseEntity.ok("User logged out successfully.");
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAccount(@RequestBody PasswordDTO dto, @RequestHeader("Authorization") String authHeader) {
        userService.deleteAccount(dto, getUserIdFromHeader(authHeader));
        tokenBlacklist.add(getTokenFromHeader(authHeader));
        return ResponseEntity.ok("Account deleted successfully.");
    }

    @PutMapping("/users")
    public UserWithoutPasswordDTO edit(@Valid @RequestBody EditDTO dto, @RequestHeader("Authorization") String authHeader) {
        return userService.edit(dto, getUserIdFromHeader(authHeader));
    }

    @PostMapping("/users/{followedId}/follow")
    public FollowDTO follow(@PathVariable int followedId, @RequestHeader("Authorization") String authHeader) {
        return userService.follow(getUserIdFromHeader(authHeader), followedId);
    }

    @PostMapping("/users/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO dto, @RequestHeader("Authorization") String authHeader) {
        userService.changePassword(dto, getUserIdFromHeader(authHeader));
        return ResponseEntity.ok("Password changed successful.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        userService.resetPassword(dto);
        return ResponseEntity.ok().body("Password reset successfully");
    }

    @GetMapping("/users/all")
    public List<UserWithoutPasswordDTO> getAll(){
        return userService.getAll();
    }


    @GetMapping("/users/followers")
    public Page<UserBasicInfoDTO> getFollowersWithPagination(@RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestHeader("Authorization") String authHeader) {
        return userService.getFollowers(pageNumber, getUserIdFromHeader(authHeader));
    }

    @GetMapping("/users/followed")
    public Page<UserBasicInfoDTO> getFollowed(@RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestHeader("Authorization") String authHeader) {
        return userService.getFollowedUsers(pageNumber, getUserIdFromHeader(authHeader));
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code){
        return userService.verifyAccount(code);
    }
}

