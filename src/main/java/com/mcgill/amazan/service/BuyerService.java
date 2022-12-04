package com.mcgill.amazan.service;

import com.mcgill.amazan.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class BuyerService {

    private final Amazan amazan;
    private final UserService userService;
    private final SellerService sellerService;

    @Autowired
    public BuyerService(Amazan amazan, UserService userService, SellerService sellerService){
        this.amazan = amazan;
        this.userService = userService;
        this.sellerService = sellerService;
    }

    public Buyer createBuyer(String firstName, String lastName, String email, String username,
                                    String passwordHash, String creditCard, String creditCardPassword){

        Buyer buyer = new Buyer(firstName, lastName, email, username, passwordHash, false, false,
                creditCard, creditCardPassword);
        buyer = userService.addUser(buyer) ? buyer : null;
        return buyer;
    }

    private Buyer getBuyer(String username){
        User user = userService.getUser(username);
        if(user instanceof Buyer){
            return (Buyer) user;
        }
        return null;
    }

    public Item addToCart(Buyer buyer, Seller seller, String itemName){
        Item item = null;
        item = seller.getItemWithName(itemName);
        if(item != null){
            buyer.getCart().addItem(item);
        }
        return item;
    }

    public Item removeFromCart(Buyer buyer, Seller seller, String itemName){
        Item item = null;
        item = seller.getItemWithName(itemName);
        if(item != null){
            item = buyer.getCart().removeItem(item.getUuid()) ? item : null;
        }
        return item;
    }

    public String checkout(Buyer buyer){
        boolean failed = false;
        HashMap<Item, Integer> itemCount = new HashMap<>();
        Cart cart = buyer.getCart();
        for(Item item: cart.getItems()){
            Integer count = itemCount.get(item);
            int actualCount = count == null ? 1 : Math.min(count + 1, item.getQuantity());
            if(count == null || actualCount != count){
                cart.removeItem(item.getUuid());
                itemCount.put(item, actualCount);
            }
        }

        String[] report = new String[itemCount.size() + 1];
        float total = 0;
        int i = 0;

        for(Item item: itemCount.keySet()){
            int quantity = itemCount.get(item);
            float miniTotal = item.getPrice() * quantity;
            report[i++] = quantity + " of " + item.getName() + " at " + item.getPrice() + " per unit | " + miniTotal;
            total += miniTotal;
        }

        report[i] = "Grand total: " + total;
        return String.join("\n", report);

    }


}
