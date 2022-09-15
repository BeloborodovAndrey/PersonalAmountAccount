package com.web.personalaccountwithcurrency.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/registration")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void addUser() throws Exception {
        mockMvc.perform(put("/registration?login=login&password=password")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void userLogout() throws Exception {
        mockMvc.perform(get("/userLogout")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}