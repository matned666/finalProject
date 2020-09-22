package eu.mnrdesign.matned.final_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.mnrdesign.matned.final_project.dto.BasketItemDTO;
import eu.mnrdesign.matned.final_project.dto.ProjectDTO;
import eu.mnrdesign.matned.final_project.model.Project;
import eu.mnrdesign.matned.final_project.service.BasketService;
import eu.mnrdesign.matned.final_project.service.ProjectService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {

    @MockBean
    private BasketService basketService;

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("GET:/basket test status 200")
    @WithMockUser(roles = "ADMIN")
    void getBasket() throws Exception {
        List<BasketItemDTO> mockedBasketItems = new LinkedList<>();
        mockedBasketItems.add(new BasketItemDTO(new Project(), 5));
        mockedBasketItems.add(new BasketItemDTO(new Project(), 10));

        doReturn(mockedBasketItems).when(basketService).getProjects();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/basket", mockedBasketItems.get(0)))
                .andExpect(status().isOk())
                .andExpect(view().name("basket"))
                .andExpect(content().string(containsString("<span>10</span>")))
                .andExpect(content().string(Matchers.not("<span>11</span>")));
    }

    @Test
    void getProjectBasketAdd() {
    }
}