package com.example.projectlivingtogether.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("admin test")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void itemRegisterTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("not admin test")
    @WithMockUser(username = "user", roles = "USER")
    public void itemRegisterTest2() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andExpect(status().isForbidden());
    }
}
