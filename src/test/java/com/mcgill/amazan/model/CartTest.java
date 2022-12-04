package com.mcgill.amazan.model;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {

    @Test
    public void testAddItemOnce(){
        Cart cart = new Cart();
        Item expectedItem = new Item();
        expectedItem.setName("artwork");
        cart.addItem(expectedItem);
        Collection<Item> actualItems = cart.getItemsWithName("artwork");
        assertEquals(1, actualItems.size());
        assertEquals(expectedItem, actualItems.toArray(Item[]::new)[0]);
    }

    @Test
    public void testAddItemTwice(){
        Cart cart = new Cart();
        Item expectedItem = new Item();
        expectedItem.setName("artwork");
        cart.addItem(expectedItem);
        cart.addItem(expectedItem);
        Collection<Item> actualItems = cart.getItemsWithName("artwork");
        assertEquals(2, cart.getItems().size());
        assertEquals(2, actualItems.size());
        assertEquals(expectedItem, actualItems.toArray(Item[]::new)[0]);
        assertEquals(expectedItem, actualItems.toArray(Item[]::new)[1]);
    }

    @Test
    public void testRemoveItem(){
        Cart cart = new Cart();
        Item expectedItem = new Item();
        expectedItem.setName("artwork");
        expectedItem.setUuid("artwork");
        cart.addItem(expectedItem);
        Item actualItem = cart.removeItem("artwork");
        assertEquals(expectedItem, actualItem);
        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testRemoveItemTwice(){
        Cart cart = new Cart();
        Item expectedItem = new Item();
        expectedItem.setName("artwork");
        expectedItem.setUuid("artwork");
        cart.addItem(expectedItem);
        cart.addItem(expectedItem);

        Item actualItem = cart.removeItem("artwork");

        assertEquals(expectedItem, actualItem);
        assertEquals(1, cart.getItems().size());

        actualItem = cart.removeItem("artwork");
        assertEquals(expectedItem, actualItem);
        assertEquals(0, cart.getItems().size());
    }
}
