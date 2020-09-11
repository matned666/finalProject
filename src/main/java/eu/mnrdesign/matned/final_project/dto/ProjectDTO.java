package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.Project;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_TIME_FORMATTER_TASK;

public class ProjectDTO {


    @NotNull(message = "Name of the project cannot be empty")
    @Size(min = 2, max = 30 , message = "Name should be between {min} and {max} signs.")
    private String name;
    private String description;
    private String imageUrl;

//    generated values
    private List<ProjectTaskDTO> tasks;
    private String userLogin;

//    audit
    private Long id;
    private String creationTime;
    private String updateTime;

    public ProjectDTO() {
    }

    public static ProjectDTO apply(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setImageUrl(project.getImageUrl());
        projectDTO.setUserLogin(project.getUser().getLogin());
        projectDTO.setTasks(ProjectTaskDTO.convertToDTOList(project.getTasks()));
        projectDTO.setCreationTime(project.getCreationDate().format(DATE_TIME_FORMATTER_TASK));
        if (project.getUpdateDate() != null) projectDTO.setUpdateTime(project.getUpdateDate().format(DATE_TIME_FORMATTER_TASK));
        projectDTO.setId(project.getId());
        return projectDTO;
    }

    public static List<ProjectDTO> convertToDTOList(List<Project> all) {
        List<ProjectDTO> result = new LinkedList<>();
        for (Project p: all) {
            result.add(ProjectDTO.apply(p));
        }
        return result;
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

    public List<ProjectTaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProjectTaskDTO> tasks) {
        this.tasks = tasks;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
