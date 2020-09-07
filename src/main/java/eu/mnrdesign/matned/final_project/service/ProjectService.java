package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.model.Project;
import eu.mnrdesign.matned.final_project.repository.ProjectRepository;
import eu.mnrdesign.matned.final_project.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectTaskRepository projectTaskRepository) {
        this.projectRepository = projectRepository;
        this.projectTaskRepository = projectTaskRepository;
    }


    public List<Project> findAll(){
        return null;
    }

    private void createProject(){

    }

}
