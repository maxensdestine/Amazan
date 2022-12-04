package com.mcgill.amazan.service;

import com.mcgill.amazan.model.Amazan;
import com.mcgill.amazan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final Amazan amazan;

    @Autowired
    public UserService(Amazan amazan){
        this.amazan = amazan;
    }

    public boolean banUser(String username){
        User user = amazan.getUserWithUsername(username);
        if(user != null){
            user.setIsBanned(true);
            return true;
        }
        return false;
    }

    public boolean deleteUser(String username){
        User user = amazan.getUserWithUsername(username);
        if(user != null){
            user.setIsDeleted(true);
            return true;
        }
        return false;
    }

    public boolean addUser(User user){
        return amazan.addUser(user);
    }

    public User getUser(String username){
        return amazan.getUserWithUsername(username);
    }

    public boolean authenticate(String username, String password){
        User user = getUser(username);
        return user.authenticate(password);
    }
}
