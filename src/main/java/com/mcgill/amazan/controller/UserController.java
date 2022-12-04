package com.mcgill.amazan.controller;

import com.mcgill.amazan.model.User;
import com.mcgill.amazan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PutMapping("/ban/{username}")
    public String banUser(@PathVariable String username){
        boolean success = userService.banUser(username);
        if(success){
            return "User successfully banned";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, username + " not found");
        }
    }

    @PutMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        boolean success = userService.deleteUser(username);
        if(success){
            return "User successfully deleted";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, username + " not found");
        }
    }

    @GetMapping("/user/{username}")
    public String getUser(@PathVariable String username){
        return userService.getUser(username).toString();
    }

}
