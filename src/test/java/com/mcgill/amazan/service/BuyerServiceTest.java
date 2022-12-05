package com.mcgill.amazan.service;

import com.mcgill.amazan.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BuyerServiceTest {
    @Autowired
    private Amazan amazan;
    @Autowired
    private BuyerService buyerService;

    private final String USERNAME = "Joe", SELLER_USERNAME = "sam", FILLER_STRING = "Two words";

    @Test
    @DirtiesContext
    public void testCreateBuyer() {
        Buyer expectedBuyer = new Buyer(FILLER_STRING, FILLER_STRING, FILLER_STRING, USERNAME,
                FILLER_STRING, false, false, FILLER_STRING, FILLER_STRING);
        expectedBuyer = buyerService.createBuyer(expectedBuyer);
        assertEquals(expectedBuyer, buyerService.getBuyer(USERNAME));
    }

    @Test
    @DirtiesContext
    public void testAddItem() {
        String itemName = "art";
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername(USERNAME);
        Seller seller = new Seller();
        seller.setUsername(SELLER_USERNAME);
        Item expectedItem = new Item();
        expectedItem.setName(itemName);
        seller.addItem(expectedItem);
        amazan.addUser(buyer);
        amazan.addUser(seller);
        Item actualItem = buyerService.addToCart(buyer, seller, itemName);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    @DirtiesContext
    public void testRemoveItem() {
        String itemName = "art";
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername(USERNAME);
        Seller seller = new Seller();
        seller.setUsername(SELLER_USERNAME);
        Item expectedItem = new Item();
        expectedItem.setName(itemName);
        seller.addItem(expectedItem);
        amazan.addUser(buyer);
        amazan.addUser(seller);
        buyerService.addToCart(buyer, seller, itemName);
        Item actualItem = buyerService.removeFromCart(buyer, seller, itemName);
        assertEquals(0, buyerService.getBuyer(USERNAME).getCart().getItems().size());
        assertEquals(expectedItem, actualItem);
    }
}
