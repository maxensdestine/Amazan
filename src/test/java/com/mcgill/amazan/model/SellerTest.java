package com.mcgill.amazan.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerTest {

    @Test
    public void testAddItemOnce(){
        Seller seller = new Seller();
        Item expectedItem = new Item();
        expectedItem.setName("artwork");
        seller.addItem(expectedItem);
        Item actualItem = seller.getItemWithName("artwork");
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testAddItemTwice(){
        Seller seller = new Seller();
        Item expectedItem = new Item();
        expectedItem.setName("artwork");
        seller.addItem(expectedItem);
        seller.addItem(expectedItem);
        assertEquals(1, seller.getItems().size());
    }
}
