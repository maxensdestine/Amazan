package com.mcgill.amazan.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import static com.mcgill.amazan.model.Utils.isNotNullOrEmpty;

public class User
{
  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;
  private boolean isDeleted;
  private boolean isBanned;

  public User(){}

  public User(String firstName, String lastName, String email, String username, String password, boolean isDeleted, boolean isBanned)
  {
    checkFields(firstName, lastName, email, username, password);
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.password = password;
    this.isDeleted = isDeleted;
    this.isBanned = isBanned;
  }

  private void checkFields(String firstName, String lastName, String email, String username, String password){
    ArrayList<String> nullFields = new ArrayList<>();
    if(!isNotNullOrEmpty(firstName)){
      nullFields.add("firstName");
    }
    if(!isNotNullOrEmpty(lastName)){
      nullFields.add("lastName");
    }
    if(!isNotNullOrEmpty(email)){
      nullFields.add("email");
    }
    if(!isNotNullOrEmpty(username)){
      nullFields.add("username");
    }
    if(!isNotNullOrEmpty(password)){
      nullFields.add("password");
    }
    if(nullFields.size() > 0){
      String fieldNames = String.join(", ", nullFields);
      throw new IllegalArgumentException(
              "Error while creating a user because the following fields were null or empty:" + fieldNames);
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String firstName)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(firstName)){
      this.firstName = firstName;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setLastName(String lastName)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(lastName)){
      this.lastName = lastName;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setEmail(String email)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(email)){
      this.email = email;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setUsername(String username)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(username)){
      this.username = username;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setPassword(String password)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(password)){
      this.password = password;
      wasSet = true;
    }
    return wasSet;
  }

  public void setIsDeleted(boolean isDeleted)
  {
    this.isDeleted = isDeleted;
  }

  public void setIsBanned(boolean isBanned)
  {
    this.isBanned = isBanned;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getEmail()
  {
    return email;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public boolean getIsDeleted()
  {
    return isDeleted;
  }

  public boolean getIsBanned()
  {
    return isBanned;
  }

  public boolean authenticate(String password){
    return this.password.equals(password);
  }

  public String toString()
  {
    return super.toString() + "["+
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "isDeleted" + ":" + getIsDeleted()+ "," +
            "isBanned" + ":" + getIsBanned()+ "]";
  }
}