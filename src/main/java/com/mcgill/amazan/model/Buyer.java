package com.mcgill.amazan.model;

public class Buyer extends User
{

  private String creditCard;
  private String creditCardPassword;
  private Cart cart;

  public Buyer(){}

  public static Buyer createBuyer(Buyer buyer){
    return new Buyer(buyer.getFirstName(), buyer.getLastName(), buyer.getEmail(), buyer.getUsername(),
            buyer.getPassword(), buyer.getIsDeleted(), buyer.getIsBanned(),
            buyer.getCreditCard(), buyer.getCreditCardPassword());
  }

  public Buyer(String firstName, String lastName, String email, String username, String password,
               boolean isDeleted, boolean isBanned, String creditCard, String creditCardPassword)
  {
    super(firstName, lastName, email, username, password, isDeleted, isBanned);
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