package com.mcgill.amazan.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AmazanTest {

    @Test
    public void testAddNewUser(){
        Amazan amazan = new Amazan();
        User expectedUser = new User();
        expectedUser.setUsername("joe");
        amazan.addUser(expectedUser);
        User actual = amazan.getUserWithUsername("joe");
        assertEquals(expectedUser, actual);
    }

    @Test
    public void testAddAlreadyAddedUser(){
        Amazan amazan = new Amazan();
        User expectedUser = new User();
        expectedUser.setUsername("joe");
        amazan.addUser(expectedUser);

        assertFalse(amazan.addUser(expectedUser));
        assertEquals(1, amazan.getUsers().size());
    }
}
