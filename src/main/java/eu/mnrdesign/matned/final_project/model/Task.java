package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.TaskDTO;
import eu.mnrdesign.matned.final_project.holder.AccountHolder;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Task extends BaseEntity{


    private String taskName;
    @Column(length = 1000)
    private String description;
    @Embedded
    private TaskDetails taskDetails;

    public Task() {
        setCreationDate(LocalDateTime.now());
    }

    public static Task apply(TaskDTO taskDTO){
        Task task = new Task();
        task.taskName = taskDTO.getTaskName();
        task.description = taskDTO.getDescription();
        task.taskDetails = new TaskDetails();
        task.taskDetails.setAuthorId(AccountHolder.getInstance().getSelectedAccountId());
        task.taskDetails.setComplicity(Complicity.valueOf(taskDTO.getComplicity()));
        task.taskDetails.setImageUrl(taskDTO.getImageUrl());
        task.taskDetails.setTimeInMinutes(taskDTO.getTimeInMinutes());
        task.taskDetails.setPrice(taskDTO.getPrice());
        return task;

    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskDetails getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(TaskDetails taskDetails) {
        this.taskDetails = taskDetails;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", taskDetails=" + taskDetails +
                '}';
    }
}
