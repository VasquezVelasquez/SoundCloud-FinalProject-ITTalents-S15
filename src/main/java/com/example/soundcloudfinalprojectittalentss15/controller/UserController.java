package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.LoginDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.RegisterDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
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
//
//    @PostMapping("/users/logout")
//    public UserWithoutPasswordDTO logout(HttpSession s) {
//        return null;
//    }
//
//    //todo what would be the return type
//    @DeleteMapping("/users")
//    public void deleteAccount(HttpSession s) {
//        return;
//    }
//
//    @PutMapping("/users")
//    public UserWithoutPasswordDTO edit(@RequestBody EditDTO dto) {
//        return null;
//    }
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
//    @PostMapping("/users/password")
//    public UserWithoutPasswordDTO changePassword(@RequestBody ChangePasswordDTO dto) {
//        return null;
//    }


}

