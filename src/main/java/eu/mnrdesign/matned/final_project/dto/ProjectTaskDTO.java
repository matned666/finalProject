package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.Task;

public class ProjectTaskDTO {

    private Integer positionInProject;
    private Task task;

    public ProjectTaskDTO() {
    }

    public Integer getPositionInProject() {
        return positionInProject;
    }

    public void setPositionInProject(Integer positionInProject) {
        this.positionInProject = positionInProject;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
