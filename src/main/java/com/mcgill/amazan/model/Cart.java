package com.mcgill.amazan.model;
import java.util.*;
import java.util.stream.Collectors;

public class Cart
{

  private List<Item> items = new ArrayList<Item>();

  public Cart() {}

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

  public Collection<Item> getItemsWithName(String name){
    Collection<Item> collection = items.stream().filter(item -> item.getName().equals(name))
            .collect(Collectors.toUnmodifiableList());
    return collection;
  }

  /**
   * Add an item to this seller
   * @param item the Item object which should be added
   * @return true if the insertion was successful, false otherwise
   */
  public boolean addItem(Item item)
  {
    boolean wasSet = false;
      items.add(item);
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
   * Removes the first item with the given uuid
   * @param uuid the uudi of the item to remove
   * @return true if an item was removed, false otherwise
   */
  public boolean removeItem(String uuid){
      return items.removeIf(item -> item.getUuid().equals(uuid));
  }

  public void removeAllItems(){
    items.clear();
  }



}