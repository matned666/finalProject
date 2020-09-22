package eu.mnrdesign.matned.final_project.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class AccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /accessDenied test - status 200")
    @WithMockUser(roles = "ADMIN")
    void accessDeniedPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/accessDenied")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"));
    }

    @Test
    @DisplayName("GET /users-list test for user account - status 403")
    @WithMockUser(roles = "USER")
    void accessDeniedPageWhenNotLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users-list")
        )
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("")));
    }


}