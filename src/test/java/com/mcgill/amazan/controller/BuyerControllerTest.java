package com.mcgill.amazan.controller;

import com.mcgill.amazan.model.*;
import com.mcgill.amazan.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BuyerControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Test
    public void testGetBuyer() throws Exception {
        Buyer user = new Buyer();
        user.setUsername("joe");
        userService.addUser(user);

        mockMvc.perform(get("/buyer/" + user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\":null,\"lastName\":null,\"email\":null," +
                        "\"username\":\"joe\",\"password\":null,\"isDeleted\":false,\"isBanned\":false," +
                        "\"creditCard\":null,\"creditCardPassword\":null,\"cart\":null}"));
    }

    @Test
    public void testCreateBuyer() throws Exception {
        String filler = "filler";
        Buyer buyer = new Buyer(filler,filler,filler,filler,filler,false,false,filler,filler);
        mockMvc.perform(post("/buyer").content("{\n" +
                                "\"username\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"lastName\": \"" + buyer.getLastName() + "\",\n" +
                                "\"email\": \"" + buyer.getEmail() + "\",\n" +
                                "\"firstName\": \"" + buyer.getFirstName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\":\"filler\",\"lastName\":\"filler\"," +
                        "\"email\":\"filler\",\"username\":\"filler\",\"password\":\"filler\",\"isDeleted\"" +
                        ":false,\"isBanned\":false,\"creditCard\":null,\"creditCardPassword\":null," +
                        "\"cart\":{\"items\":[]}}"));
    }

    @Test
    public void testAddItem() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/add").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"item\",\"sellerUsername\":\"seller\"}"));
    }

    @Test
    public void testAddItemWrongPassword() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/add").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "1" + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The password was incorrect or the buyer is banned/deleted"));
    }

    @Test
    public void testAddItemInexistentBuyer() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/add").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "1" + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This buyer does not exist"));
    }

    @Test
    public void testAddItemInexistentSeller() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/add").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "1" + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This seller does not exist"));
    }

    @Test
    public void testRemoveItem() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        buyer.getCart().addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/remove").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"item\",\"sellerUsername\":\"seller\"}"));
    }

    @Test
    public void testRemoveItemWrongPassword() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        buyer.getCart().addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/remove").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "1" + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The password was incorrect or the buyer is banned/deleted"));
    }

    @Test
    public void testRemoveItemInexistentBuyer() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        buyer.getCart().addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/remove").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "1" + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This buyer does not exist"));
    }

    @Test
    public void testRemoveItemInexistentSeller() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setCart(new Cart());
        buyer.setUsername("joe");
        buyer.setPassword("123");
        Seller seller = new Seller();
        seller.setUsername("seller");
        Item item = new Item();
        item.setName("item");
        seller.addItem(item);
        buyer.getCart().addItem(item);
        userService.addUser(buyer);
        userService.addUser(seller);

        mockMvc.perform(post("/buyer/remove").content("{\n" +
                                "\"buyer\": \"" + buyer.getUsername() + "\",\n" +
                                "\"password\": \"" + buyer.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "1" + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This seller does not exist"));
    }

}
