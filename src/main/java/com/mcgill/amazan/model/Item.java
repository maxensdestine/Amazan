package com.mcgill.amazan.model;

import java.util.*;
import static com.mcgill.amazan.model.Utils.isNotNullOrEmpty;

public class Item
{
  private float price;
  private int quantity;
  private String name;
  private String description;
  private String sellerUsername;
  private String uuid = UUID.randomUUID().toString();


  public Item(){}

  public static Item createItem(Item item){
    return new Item(item.getPrice(), item.getQuantity(), item.getName(),
            item.getDescription(), item.getSellerUsername());
  }

  public Item(float price, int quantity, String name, String description, String sellerUsername)
  {
    this.price = price;
    this.quantity = quantity;
    this.name = name;
    this.description = description;
    this.sellerUsername = sellerUsername;
  }

  private void checkFields(float price, String name, String description, String sellerUsername){
    ArrayList<String> nullFields = new ArrayList<>();
    if(price < 0){
      nullFields.add("price");
    }
    if(!isNotNullOrEmpty(name)){
      nullFields.add("name");
    }
    if(!isNotNullOrEmpty(description)){
      nullFields.add("description");
    }
    if(sellerUsername == null){
      nullFields.add("sellerUsername");
    }
    if(nullFields.size() > 0){
      String fieldNames = String.join(", ", nullFields);
      throw new IllegalArgumentException(
              "Error while creating an item because the following fields were null or empty or invalid:" + fieldNames);
    }
  }

  public boolean setPrice(float price)
  {
    boolean wasSet = false;
    if(price >= 0){
      this.price = price;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setName(String name)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(name)){
      this.name = name;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setDescription(String description)
  {
    boolean wasSet = false;
    if(isNotNullOrEmpty(description)){
      this.description = description;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setSellerUsername(String sellerUsername) {
    boolean wasSet = false;
    if(isNotNullOrEmpty(sellerUsername)){
      this.sellerUsername= sellerUsername;
      wasSet = true;
    }
    return wasSet;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean setQuantity(int quantity) {
    boolean wasSet = false;
    if(quantity >= 0){
      this.quantity = quantity;
      wasSet = true;
    }
    return wasSet;
  }

  public float getPrice()
  {
    return price;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getSellerUsername() {
    return sellerUsername;
  }

  public String getUuid() {
    return uuid;
  }

  public int getQuantity() {
    return quantity;
  }

  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "]";
  }
}