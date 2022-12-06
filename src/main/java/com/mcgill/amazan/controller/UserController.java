package com.mcgill.amazan.controller;

import com.mcgill.amazan.model.Administrator;
import com.mcgill.amazan.model.User;
import com.mcgill.amazan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PutMapping("/ban")
    public String banUser(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String password = json.get("password");
        String userToBan = json.get("ban");
        User admin = userService.getUser(username);
        User bannedUser = userService.getUser(userToBan);
        if(admin == null || ! (admin instanceof Administrator)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin does not exist");
        } else if(!admin.authenticate(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        } else if(bannedUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User to be banned does not exist");
        }
        userService.banUser(userToBan);
        return "User successfully banned";

    }

    @PutMapping("/delete")
    public String deleteUser(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String password = json.get("password");
        User user = userService.getUser(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        } else if(!user.authenticate(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        userService.deleteUser(username);
        return "User successfully deleted";
    }

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username){
        return userService.getUser(username);
    }

}
