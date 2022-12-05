package com.mcgill.amazan.model;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Amazan
{
  private List<User> users = new ArrayList<>();

  public Amazan() {}

  public List<User> getUsers()
  {
    return Collections.unmodifiableList(users);
  }

  public User getUserWithUsername(String username){
    User found = users.stream().filter(user -> user.getUsername().equals(username))
            .findAny().orElse(null);
    return found;
  }

  /**
   * Add an user to this seller if the user is not owned and is not already in the list
   * @param user the User object which should be added
   * @return true if the insertion was successful, false otherwise
   */
  public boolean addUser(User user)
  {
    boolean wasSet = false;
    User old = users.stream().filter(obj -> obj.getUsername().equals(user.getUsername())).findAny().orElse(null);
    if(old == null){
      users.add(user);
      wasSet = true;
    }
    return wasSet;
  }
}