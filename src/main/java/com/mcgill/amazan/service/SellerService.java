package com.mcgill.amazan.service;

import com.mcgill.amazan.model.Amazan;
import com.mcgill.amazan.model.Seller;
import com.mcgill.amazan.model.Item;
import com.mcgill.amazan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    private final UserService userService;
    private final Amazan amazan;

    @Autowired
    public SellerService(Amazan amazan, UserService userService){
        this.userService = userService;
        this.amazan = amazan;
    }

    public Seller createSeller(String firstName, String lastName, String email, String username,
                             String passwordHash){

        Seller seller = new Seller(firstName, lastName, email, username, passwordHash, false, false);
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
