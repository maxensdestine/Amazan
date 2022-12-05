package com.mcgill.amazan.service;

import com.mcgill.amazan.model.Amazan;
import com.mcgill.amazan.model.Item;
import com.mcgill.amazan.model.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SellerServiceTest {
    @Autowired
    private Amazan amazan;
    @Autowired
    private SellerService sellerService;

    private final String USERNAME = "Joe";
    private final String FILLER_STRING = "Two words";

    @Test
    @DirtiesContext
    public void testCreateSeller() {
        Seller expectedSeller = new Seller(FILLER_STRING, FILLER_STRING, FILLER_STRING, USERNAME,
                FILLER_STRING, false, false);
        expectedSeller = sellerService.createSeller(expectedSeller);
        assertEquals(expectedSeller, sellerService.getSeller(USERNAME));
    }

    @Test
    @DirtiesContext
    public void testAddItem() {
        Seller seller = new Seller();
        seller.setUsername(USERNAME);
        amazan.addUser(seller);
        Item item = new Item(1, 1, FILLER_STRING, FILLER_STRING, USERNAME);
        item = sellerService.addItem(seller, item);
        assertEquals(item, sellerService.getSeller(USERNAME).getItemWithName(FILLER_STRING));
    }

    @Test
    @DirtiesContext
    public void testRemoveItem() {
        Seller seller = new Seller();
        seller.setUsername(USERNAME);
        amazan.addUser(seller);
        Item expectedItem = new Item(1, 1, FILLER_STRING, FILLER_STRING, USERNAME);
        sellerService.addItem(seller, expectedItem);
        Item actualItem = sellerService.removeItem(seller, expectedItem.getName());
        assertEquals(expectedItem.getName(), actualItem.getName());
        assertEquals(null, sellerService.getSeller(USERNAME).getItemWithName(FILLER_STRING));
    }
}
