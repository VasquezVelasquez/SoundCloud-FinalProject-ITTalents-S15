package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.RegisterDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

//    @GetMapping("/users/{id}")
//    public UserWithoutPasswordDTO getUsedInfo(@PathVariable int id) {
//        return null;
//    }
//
//    @PostMapping("/users/login")
//    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto) {
//        return null;
//    }
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

