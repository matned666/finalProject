package eu.mnrdesign.matned.final_project.model;

import javax.persistence.*;

public class ProjectTask extends BaseEntity{

    @OneToOne
    private Project project;

    @ManyToOne
    private Task task;

    private Integer positionInProject;

    public ProjectTask() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getPositionInProject() {
        return positionInProject;
    }

    public void setPositionInProject(Integer positionInProject) {
        this.positionInProject = positionInProject;
    }
}
