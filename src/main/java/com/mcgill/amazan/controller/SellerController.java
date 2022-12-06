package com.mcgill.amazan.controller;

import com.mcgill.amazan.model.Item;
import com.mcgill.amazan.model.Seller;
import com.mcgill.amazan.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/seller")
    public Seller createSeller(@RequestBody Seller seller) {
        try {
            return sellerService.createSeller(seller);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/seller/{username}")
    public Seller getUser(@PathVariable String username){
        return sellerService.getSeller(username);
    }

    @PostMapping("/seller/add")
    public Item addItem(@RequestBody Map<String, String> json) {
        String sellerUsername = json.get("seller");
        String password = json.get("password");
        String price = json.get("price");
        String quantity = json.get("quantity");
        String name = json.get("name");
        String description = json.get("description");
        Item item = new Item(Float.parseFloat(price), Integer.parseInt(quantity), name, description, null);
        Seller seller = sellerService.getSeller(sellerUsername);
        if (seller == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "This seller does not exist");
        } else if (!seller.authenticate(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "The password was incorrect or the seller is banned/deleted");
        } else {
            try {
                return sellerService.addItem(seller, item);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }
    }

    @PostMapping("/seller/remove")
    public Item removeItem(@RequestBody Map<String, String> json) {
        String sellerUsername = json.get("seller");
        String password = json.get("password");
        String itemName = json.get("item");
        Seller seller = sellerService.getSeller(sellerUsername);
        if (seller == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "This seller does not exist");
        } else if (!seller.authenticate(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "The password was incorrect or the seller is banned/deleted");
        } else {
            return sellerService.removeItem(seller, itemName);
        }
    }

}