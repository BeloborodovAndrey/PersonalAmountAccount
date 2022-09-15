package com.web.personalaccountwithcurrency.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLoginPage() throws Exception {
        mockMvc.perform(get("/login")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


    @Test
    void getHomePage() throws Exception {
        mockMvc.perform(get("/homePage")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getCurrentUser() throws Exception {
        mockMvc.perform(post("/currentUser")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void signUser() throws Exception {
        mockMvc.perform(post("/signIn?login=login&password=password")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getTestRates() throws Exception {
        mockMvc.perform(get("/testRates")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}