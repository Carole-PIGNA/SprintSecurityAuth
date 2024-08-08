package com.example.training.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class SecurityConfigurationTest {
    @Autowired
    MockMvc mockMvc;
        @Test
        public void SecurityFilterChain() throws Exception {
          mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/training/student"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
