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

}