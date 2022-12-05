package com.mcgill.amazan.model;

public class Administrator extends User
{

  public Administrator(String firstName, String lastName, String email, String username, String passwordHash, boolean isDeleted, boolean isBanned)
  {
    super(firstName, lastName, email, username, passwordHash, isDeleted, isBanned);
  }


}