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
public class SellerControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Test
    public void testGetSeller() throws Exception {
        Seller user = new Seller();
        user.setUsername("seller");
        userService.addUser(user);

        mockMvc.perform(get("/seller/" + user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\":null,\"lastName\":null,\"email\":null," +
                        "\"username\":\"seller\",\"password\":null,\"isDeleted\":false,\"isBanned\":false}"));
    }

    @Test
    public void testCreateSeller() throws Exception {
        String filler = "filler";
        Seller seller = new Seller(filler,filler,filler,filler,filler,false,false);
        mockMvc.perform(post("/seller").content("{\n" +
                                "\"username\": \"" + seller.getUsername() + "\",\n" +
                                "\"password\": \"" + seller.getPassword() + "\",\n" +
                                "\"lastName\": \"" + seller.getLastName() + "\",\n" +
                                "\"email\": \"" + seller.getEmail() + "\",\n" +
                                "\"firstName\": \"" + seller.getFirstName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\":\"filler\",\"lastName\":\"filler\"," +
                        "\"email\":\"filler\",\"username\":\"filler\",\"password\":\"filler\",\"isDeleted\"" +
                        ":false,\"isBanned\":false}"));
    }

    @Test
    public void testAddItem() throws Exception {
        String filler = "filler";
        Seller seller = new Seller();
        Item item = new Item(0,0,filler, filler, null);
        seller.setUsername("seller");
        seller.setPassword("123");
        userService.addUser(seller);

        mockMvc.perform(post("/seller/add").content("{\n" +
                                "\"password\": \"" + seller.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"price\": \"" + item.getPrice() + "\",\n" +
                                "\"quantity\": \"" + item.getQuantity() + "\",\n" +
                                "\"description\": \"" + item.getDescription() + "\",\n" +
                                "\"name\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"filler\",\"sellerUsername\":\"seller\"}"));
    }

    @Test
    public void testAddItemWrongPassword() throws Exception {
        String filler = "filler";
        Seller seller = new Seller();
        Item item = new Item(0,0,filler, filler, null);
        seller.setUsername("seller");
        seller.setPassword("123");
        userService.addUser(seller);

        mockMvc.perform(post("/seller/add").content("{\n" +
                                "\"password\": \"" + seller.getPassword() + "1" + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"price\": \"" + item.getPrice() + "\",\n" +
                                "\"quantity\": \"" + item.getQuantity() + "\",\n" +
                                "\"description\": \"" + item.getDescription() + "\",\n" +
                                "\"name\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The password was incorrect or the seller is banned/deleted"));
    }

    @Test
    public void testAddItemInexistentSeller() throws Exception {
        String filler = "filler";
        Item item = new Item(0,0,filler, filler, null);

        mockMvc.perform(post("/seller/add").content("{\n" +
                                "\"password\": \"" + filler + "\",\n" +
                                "\"seller\": \"" + filler + "\",\n" +
                                "\"price\": \"" + item.getPrice() + "\",\n" +
                                "\"quantity\": \"" + item.getQuantity() + "\",\n" +
                                "\"description\": \"" + item.getDescription() + "\",\n" +
                                "\"name\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This seller does not exist"));
    }


    @Test
    public void testRemoveItem() throws Exception {
        String filler = "filler";
        Seller seller = new Seller();
        Item item = new Item(0,0,filler, filler, null);
        seller.setUsername("seller");
        seller.setPassword("123");
        seller.addItem(item);
        userService.addUser(seller);

        mockMvc.perform(post("/seller/remove").content("{\n" +
                                "\"password\": \"" + seller.getPassword() + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"filler\",\"sellerUsername\":\"seller\"}"));
    }

    @Test
    public void testRemoveItemWrongPassword() throws Exception {
        String filler = "filler";
        Seller seller = new Seller();
        Item item = new Item(0,0,filler, filler, null);
        seller.setUsername("seller");
        seller.setPassword("123");
        userService.addUser(seller);

        mockMvc.perform(post("/seller/remove").content("{\n" +
                                "\"password\": \"" + seller.getPassword() + "1" + "\",\n" +
                                "\"seller\": \"" + seller.getUsername() + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The password was incorrect or the seller is banned/deleted"));
    }

    @Test
    public void testRemoveItemInexistentSeller() throws Exception {
        String filler = "filler";
        Item item = new Item(0,0,filler, filler, null);

        mockMvc.perform(post("/seller/remove").content("{\n" +
                                "\"password\": \"" + filler + "\",\n" +
                                "\"seller\": \"" + filler + "\",\n" +
                                "\"item\": \"" + item.getName() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This seller does not exist"));
    }

}
