package eu.mnrdesign.matned.final_project.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProjectDTO {

    @NotNull(message = "Name of the project cannot be empty")
    private String name;
    private String description;

//    generated values
    private List<ProjectTaskDTO> tasks;
    private String userLogin;

    public ProjectDTO() {
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
}
