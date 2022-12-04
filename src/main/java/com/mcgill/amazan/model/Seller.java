package com.mcgill.amazan.model;
import java.util.*;
import java.util.stream.IntStream;

public class Seller extends User
{

  private ArrayList<Item> items;


  public Seller(){}

  public static Seller createSeller(Seller seller){
    return new Seller(seller.getFirstName(), seller.getLastName(), seller.getEmail(), seller.getUsername(),
            seller.getPassword(), seller.getIsDeleted(), seller.getIsBanned());
  }

  public Seller(String firstName, String lastName, String email, String username, String password,
                boolean isDeleted, boolean isBanned)
  {
    super(firstName, lastName, email, username, password, isDeleted, isBanned);

  }

  public List<Item> getItems()
  {
    return Collections.unmodifiableList(items);
  }

  public Item getItemAt(int index){
    if(index < 0 || index > items.size()){
      return null;
    }
    return items.get(index);
  }

  public Item getItemWithName(String name){
    Item found = items.stream().filter(item -> item.getName().equals(name))
            .findAny().orElse(null);
    return found;
  }

  /**
   * Add an item to this seller if the item is not owned by another user and is not already in the list
   * @param item the Item object which should be added
   * @return true if the insertion was successful, false otherwise
   */
  public boolean addItem(Item item)
  {
    boolean wasSet = false;
    Item old = items.stream().filter(obj -> obj.getName().equals(item.getName())).findAny().orElse(null);
    if((item.getSellerUsername() == null || item.getSellerUsername().equals(this.getUsername())) && old == null){
      item.setSellerUsername(this.getUsername());
      items.add(item);
      wasSet = true;
    }
    return wasSet;
  }

  /**
   * Add a collection of items to this seller
   * @param items the iterable object which contains the items to be added
   * @return true if every insertion was successful, false otherwise
   */
  public boolean addItems(Iterable<Item> items)
  {
    boolean allSuccess = true;
    for(Item item: items){
      allSuccess = allSuccess && addItem(item);
    }
    return allSuccess;
  }

  /**
   * Removes the item with the given name
   * @param name the name of the item to remove
   * @return true if an item was removed, false otherwise
   */
  public Item removeItem(String name){
    int index = IntStream.range(0, items.size()).filter(i -> items.get(i).getName().equals(name))
            .findAny().orElse(-1);
    if(index != -1){
      return items.remove(index);
    }
    return null;
  }

  public void removeAllItems(){
    items.clear();
  }


}