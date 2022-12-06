package com.mcgill.amazan.controller;

import com.mcgill.amazan.model.Administrator;
import com.mcgill.amazan.model.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Test
    public void testGetUser() throws Exception {
        User user = new User();
        user.setUsername("joe");
        userService.addUser(user);

        mockMvc.perform(get("/user/" + user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\":null,\"lastName\":null,\"email\":null,\"username\":\"joe\",\"password\":null,\"isDeleted\":false,\"isBanned\":false}"));
    }

    @Test
    public void testBanUser() throws Exception {
        User user = new User();
        user.setUsername("joe");
        Administrator admin = new Administrator();
        admin.setUsername("admin");
        admin.setPassword("123");
        userService.addUser(user);
        userService.addUser(admin);

        mockMvc.perform(put("/ban").content("{\n" +
                                "\"username\": \"" + admin.getUsername() + "\",\n" +
                                "\"password\": \"" + admin.getPassword() + "\",\n" +
                                "\"ban\": \"" + user.getUsername() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("User successfully banned"));
    }

    @Test
    public void testBanUserWrongAdminPassword() throws Exception {
        User user = new User();
        user.setUsername("joe");
        Administrator admin = new Administrator();
        admin.setUsername("admin");
        admin.setPassword("123");
        userService.addUser(user);
        userService.addUser(admin);

        mockMvc.perform(put("/ban").content("{\n" +
                                "\"username\": \"" + admin.getUsername() + "\",\n" +
                                "\"password\": \"" + admin.getPassword() + "1" + "\",\n" +
                                "\"ban\": \"" + user.getUsername() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Wrong password"));
    }

    @Test
    public void testBanInexistentUser() throws Exception {
        Administrator admin = new Administrator();
        admin.setUsername("admin");
        admin.setPassword("123");
        userService.addUser(admin);

        mockMvc.perform(put("/ban").content("{\n" +
                                "\"username\": \"" + admin.getUsername() + "\",\n" +
                                "\"password\": \"" + admin.getPassword() + "\",\n" +
                                "\"ban\": \"" + "joe" + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User to be banned does not exist"));
    }

    @Test
    public void testBanUserNotAnAdmin() throws Exception {
        User user = new User();
        user.setUsername("joe");
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123");
        userService.addUser(user);
        userService.addUser(admin);

        mockMvc.perform(put("/ban").content("{\n" +
                                "\"username\": \"" + admin.getUsername() + "\",\n" +
                                "\"password\": \"" + admin.getPassword() + "\",\n" +
                                "\"ban\": \"" + user.getUsername() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Admin does not exist"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setUsername("joe");
        user.setPassword("123");
        userService.addUser(user);

        mockMvc.perform(put("/delete").content("{\n" +
                                "\"username\": \"" + user.getUsername() + "\",\n" +
                                "\"password\": \"" + user.getPassword() + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("User successfully deleted"));
    }

    @Test
    public void testDeleteUserWrongPassword() throws Exception {
        User user = new User();
        user.setUsername("joe");
        user.setPassword("123");
        userService.addUser(user);

        mockMvc.perform(put("/delete").content("{\n" +
                                "\"username\": \"" + user.getUsername() + "\",\n" +
                                "\"password\": \"" + user.getPassword() + "1" + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Wrong password"));
    }

    @Test
    public void testDeleteInexistentUser() throws Exception {
        mockMvc.perform(put("/delete").content("{\n" +
                                "\"username\": \"" + "joe" + "\",\n" +
                                "\"password\": \"" + "123" + "\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User does not exist"));
    }
}
