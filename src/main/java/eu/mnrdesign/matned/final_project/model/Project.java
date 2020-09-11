package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.ProjectDTO;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_PATTERN;

@Entity
public class Project extends BaseEntity {

    private String name;
    private String description;
    private String imageUrl;
    private LocalDate projectStart;
    private LocalDate projectDeadline;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
        try {
            project.setProjectStart(LocalDate.parse(projectDTO.getProjectStart(), DateTimeFormatter.ofPattern(DATE_PATTERN)));
        } catch (DateTimeParseException e) {
            project.setProjectStart(null);
        }
        try {
            project.setProjectDeadline(LocalDate.parse(projectDTO.getProjectDeadline(), DateTimeFormatter.ofPattern(DATE_PATTERN)));
        } catch (DateTimeParseException e) {
            project.setProjectDeadline(null);
        }
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

    public LocalDate getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(LocalDate projectStart) {
        this.projectStart = projectStart;
    }

    public LocalDate getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(LocalDate projectDeadline) {
        this.projectDeadline = projectDeadline;
    }
}
