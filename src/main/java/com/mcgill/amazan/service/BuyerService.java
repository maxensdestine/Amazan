package com.mcgill.amazan.service;

import com.mcgill.amazan.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class BuyerService {

    private final UserService userService;

    @Autowired
    public BuyerService(UserService userService){
        this.userService = userService;
    }

    public Buyer createBuyer(Buyer toBeCreated){

        Buyer buyer = Buyer.createBuyer(toBeCreated);
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
            item = buyer.getCart().removeItem(item.getUuid());
        }
        return item;
    }

    public String checkout(Buyer buyer){
        boolean failed = false;
        HashMap<Item, Integer> itemCount = new HashMap<>();
        Cart cart = buyer.getCart();
        for(Item item: cart.getItems()){
            if(item.getQuantity() == 0){
                continue;
            }
            Integer count = itemCount.get(item);
            int actualCount = count == null ? 1 : count + 1;
            cart.removeItem(item.getUuid());
            item.setQuantity(item.getQuantity() - 1);
            itemCount.put(item, actualCount);

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
