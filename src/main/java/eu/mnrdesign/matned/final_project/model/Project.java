package eu.mnrdesign.matned.final_project.model;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

public class Project extends BaseEntity{

    private String name;
    private String description;
    @ManyToOne
    private User user;

    @OneToOne
    private List<ProjectTask> tasks;

    public Project() {
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
