package com.mcgill.amazan.service;

import com.mcgill.amazan.model.Amazan;
import com.mcgill.amazan.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private Amazan amazan;
    @Autowired
    private UserService userService;

    private final String USERNAME = "Joe";

    @Test
    @DirtiesContext
    public void testBanUser(){
        User user = new User();
        user.setUsername(USERNAME);
        amazan.addUser(user);
        assertFalse(user.getIsBanned());
        assertTrue(userService.banUser(USERNAME));
        assertTrue(user.getIsBanned());
    }

    @Test
    public void testBanNonExistentUser(){
        assertFalse(userService.banUser(USERNAME));
    }

    @Test
    @DirtiesContext
    public void testDeleteUser(){
        User user = new User();
        user.setUsername(USERNAME);
        amazan.addUser(user);
        assertFalse(user.getIsDeleted());
        assertTrue(userService.deleteUser(USERNAME));
        assertTrue(user.getIsDeleted());
    }

    @Test
    public void testDeleteNonExistentUser(){
        assertFalse(userService.deleteUser(USERNAME));
    }
    
    @Test
    @DirtiesContext
    public void testGetUser(){
        
    }

    @Test
    public void testGetNonExistentUser(){
        assertNull(userService.getUser(USERNAME));
    }

    @Test
    @DirtiesContext
    public void testAuthenticateUser(){
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("123");
        amazan.addUser(user);
        userService.authenticate(USERNAME, "123");
    }

    @Test
    @DirtiesContext
    public void testAuthenticateBannedUser(){
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("123");
        user.setIsBanned(true);
        amazan.addUser(user);
        userService.authenticate(USERNAME, "123");
    }

    @Test
    @DirtiesContext
    public void testAuthenticateDeletedUser(){
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("123");
        user.setIsDeleted(true);
        amazan.addUser(user);
        userService.authenticate(USERNAME, "123");
    }
}
