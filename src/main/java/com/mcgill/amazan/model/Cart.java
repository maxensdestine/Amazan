package com.mcgill.amazan.model;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cart
{

  private List<Item> items = new ArrayList<>();

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

  public void addItem(Item item)
  {
      items.add(item);
  }

  /**
   * Add a collection of items to this seller
   * @param items the iterable object which contains the items to be added
   * @return true if every insertion was successful, false otherwise
   */
  public void addItems(Collection<Item> items)
  {
    this.items.addAll(items);
  }

  /**
   * Removes the first item with the given uuid
   * @param uuid the uudi of the item to remove
   * @return the removed item or null if nothing was removed
   */
  public Item removeItem(String uuid){
    int index = IntStream.range(0, items.size()).filter(i -> items.get(i).getUuid().equals(uuid))
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