package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.ProjectTaskDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class ProjectTask extends BaseEntity implements Comparable<ProjectTask>{

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Project project;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Task task;

    private boolean isDone;

    private Integer positionInProject;

    public ProjectTask() {
    }

    public ProjectTask(Project project, Task task) {
        this.creationDate = LocalDateTime.now();
        this.project = project;
        this.task = task;
        this.positionInProject = project.getTasks().size()+1;
        this.isDone = false;
    }

    private static ProjectTask apply(Task task, Project project, ProjectTaskDTO pt) {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setPositionInProject(pt.getPositionInProject());
        projectTask.setTask(task);
        projectTask.setProject(project);
        projectTask.setDone(pt.isDone());
        return null;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
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

    @Override
    public int compareTo(ProjectTask o) {
        return this.getPositionInProject() - o.getPositionInProject();
    }
}
