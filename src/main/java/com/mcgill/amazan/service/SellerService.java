package com.mcgill.amazan.service;

import com.mcgill.amazan.model.Amazan;
import com.mcgill.amazan.model.Seller;
import com.mcgill.amazan.model.Item;
import com.mcgill.amazan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final UserService userService;

    @Autowired
    public SellerService(UserService userService){
        this.userService = userService;
    }

    public Seller createSeller(Seller toBeCreated){

        Seller seller = Seller.createSeller(toBeCreated);
        seller = userService.addUser(seller) ? seller : null;
        return seller;
    }

    public Seller getSeller(String username){
        User user = userService.getUser(username);
        if(user instanceof Seller){
            return  (Seller) user;
        }
        return null;
    }

    public Item addItem(Seller seller, Item item){
        item = seller.addItem(item) ? item : null;
        return item;
    }

    public Item removeItem(Seller seller, String itemName){
        return seller.removeItem(itemName);
    }
}
