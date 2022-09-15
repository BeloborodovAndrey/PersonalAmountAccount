package com.web.personalaccountwithcurrency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.personalaccountwithcurrency.model.RateDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class ConvertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void convert() throws Exception {
        RateDto rateDto = new RateDto("USD", "60");
        mockMvc.perform(post("/convert")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rateDto)))
                .andExpect(status().isOk());
    }
}