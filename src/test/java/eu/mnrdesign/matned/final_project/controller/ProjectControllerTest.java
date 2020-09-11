package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.dto.ProjectDTO;
import eu.mnrdesign.matned.final_project.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @MockBean
    private ProjectService service;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "admin")
    @Test
    @DisplayName("GET /project/1 test - product found 200")
    public void testGETProdFnd() throws Exception {

        ProjectDTO mockedProject = new ProjectDTO();
        doReturn(mockedProject).when(service).findProjectById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("project")));
    }

}