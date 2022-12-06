package com.mcgill.amazan.controller;

import com.mcgill.amazan.model.Buyer;
import com.mcgill.amazan.model.Item;
import com.mcgill.amazan.model.Seller;
import com.mcgill.amazan.model.User;
import com.mcgill.amazan.service.BuyerService;
import com.mcgill.amazan.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class BuyerController {

    private final BuyerService buyerService;
    private final SellerService sellerService;

    @Autowired
    public BuyerController(BuyerService buyerService, SellerService sellerService) {
        this.buyerService = buyerService;
        this.sellerService = sellerService;
    }

    @PostMapping("/buyer")
    public User createBuyer(@RequestBody Buyer buyer) {
        try {
            return buyerService.createBuyer(buyer);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/buyer/{username}")
    public Buyer getUser(@PathVariable String username){
        return buyerService.getBuyer(username);
    }

    @PostMapping("/buyer/add")
    public Item addToCart(@RequestBody Map<String, String> json) {
        User[] users = getBuyerAndSeller(json);
        Buyer buyer = (Buyer) users[0];
        Seller seller = (Seller) users[1];
        String itemName = json.get("item");
        return buyerService.addToCart(buyer, seller, itemName);
    }

    @PostMapping("/buyer/remove")
    public Item removeFromCart(@RequestBody Map<String, String> json) {
        User[] users = getBuyerAndSeller(json);
        Buyer buyer = (Buyer) users[0];
        Seller seller = (Seller) users[1];
        String itemName = json.get("item");
        return buyerService.removeFromCart(buyer, seller, itemName);

    }

    @PostMapping("/buyer/checkout")
    public String checkout(@RequestBody Map<String, String> json) {
        String buyerUsername = json.get("buyer");
        String password = json.get("password");
        Buyer buyer = buyerService.getBuyer(buyerUsername);
        if (buyer == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "This buyer does not exist");
        } else if (!buyer.authenticate(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "The password was incorrect or the buyer is banned/deleted");
        } else {
            return buyerService.checkout(buyer);
        }
    }

    private User[] getBuyerAndSeller(Map<String, String> json){
        String buyerUsername = json.get("buyer");
        String sellerUsername = json.get("seller");
        String password = json.get("password");
        Buyer buyer = buyerService.getBuyer(buyerUsername);
        Seller seller = sellerService.getSeller(sellerUsername);
        if (buyer == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "This buyer does not exist");
        } else if (!buyer.authenticate(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "The password was incorrect or the buyer is banned/deleted");
        } else if (seller == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "This seller does not exist");
        }
        return new User[]{buyer, seller};
    }

}
