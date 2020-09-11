package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.model.ProjectTask;
import eu.mnrdesign.matned.final_project.model.Task;
import eu.mnrdesign.matned.final_project.model.TaskDetails;
import eu.mnrdesign.matned.final_project.repository.ProjectTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectTaskRepository projectTaskRepository;

    private List<ProjectTask> mockedListOfProjectTasks;

    @BeforeEach
    void setUp(){
        mockedListOfProjectTasks = new LinkedList();

        ProjectTask pt = new ProjectTask();
        Task task = new Task();
        TaskDetails taskDetails = new TaskDetails();
        pt.setTask(task);
        task.setTaskDetails(taskDetails);
        taskDetails.setTimeInMinutes(5);
        taskDetails.setPrice(new BigDecimal(5));
        for (int i = 0; i < 3; i++) {
            mockedListOfProjectTasks.add(pt);
        }

    }

    @Test
    void priceSum(){

        doReturn(mockedListOfProjectTasks).when(projectTaskRepository).findAllByProjectId(1L);
        BigDecimal sum = projectService.getTotalPrice(1L);

        assertEquals(sum, new BigDecimal(15));

    }

    @Test
    void timeSum(){

        doReturn(mockedListOfProjectTasks).when(projectTaskRepository).findAllByProjectId(1L);
        Integer sum = projectService.getTotalTime(1L);

        assertEquals(sum, 15);

    }

}