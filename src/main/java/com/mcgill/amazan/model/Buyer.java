package com.mcgill.amazan.model;

public class Buyer extends User
{

  private String creditCard;
  private String creditCardPassword;
  private Cart cart;

  public Buyer(){}
  public Buyer(String firstName, String lastName, String email, String username, String passwordHash,
               boolean isDeleted, boolean isBanned, String creditCard, String creditCardPassword)
  {
    super(firstName, lastName, email, username, passwordHash, isDeleted, isBanned);
    this.creditCard = creditCard;
    this.creditCardPassword = creditCardPassword;
    Cart cart = new Cart();
    this.cart = cart;
  }

  public boolean setCreditCard(String creditCard)
  {
    boolean wasSet = false;
    this.creditCard = creditCard;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreditCardPassword(String creditCardPassword)
  {
    boolean wasSet = false;
    this.creditCardPassword = creditCardPassword;
    wasSet = true;
    return wasSet;
  }

  public void setCart(Cart cart){
    this.cart = cart;
  }

  public String getCreditCard()
  {
    return creditCard;
  }

  public String getCreditCardPassword()
  {
    return creditCardPassword;
  }
  
  public Cart getCart()
  {
    return cart;
  }


  public String toString()
  {
    return super.toString() + "["+
            "creditCard" + ":" + getCreditCard()+ "," +
            "creditCardPassword" + ":" + getCreditCardPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
  }
}