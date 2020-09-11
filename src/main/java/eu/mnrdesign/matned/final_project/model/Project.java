package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.ProjectDTO;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Project extends BaseEntity{

    private String name;
    private String description;
    private String imageUrl;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectTask> tasks;

    public Project() {
        tasks = new LinkedList<>();
        this.creationDate = LocalDateTime.now();
    }

    public static Project apply(User user, ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setImageUrl(projectDTO.getImageUrl());
        project.setUser(user);
        return project;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProjectTask> tasks) {
        this.tasks = tasks;
    }
}
