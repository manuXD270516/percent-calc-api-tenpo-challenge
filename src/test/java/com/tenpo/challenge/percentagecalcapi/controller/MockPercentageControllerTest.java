package com.tenpo.challenge.percentagecalcapi.controller;

import com.tenpo.challenge.percentagecalcapi.external.MockPercentageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MockPercentageControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MockPercentageController()).build();
    }

    @Test
    void shouldReturnPercentageBetween5And10() throws Exception {
        mockMvc.perform(get("/mock/percentage")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue()))
                .andExpect(content().string(org.hamcrest.Matchers.matchesRegex("\\d+(\\.\\d+)?")))
                .andExpect(jsonPath("$", greaterThanOrEqualTo(5.0)))
                .andExpect(jsonPath("$", lessThanOrEqualTo(10.0)));
    }
}
