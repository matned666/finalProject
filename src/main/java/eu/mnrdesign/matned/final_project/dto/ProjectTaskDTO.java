package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.ProjectTask;
import eu.mnrdesign.matned.final_project.model.Task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_TIME_FORMATTER_TASK;

public class ProjectTaskDTO implements Comparable<ProjectTaskDTO>{

    private Long id;
    private String creationTime;
    private String updateTime;

    private Integer positionInProject;
//    private TaskDTO task;

    private String taskName;
    private String description;
    private Integer timeInMinutes;
    private String imageUrl;
    private BigDecimal price;
    private String category;
    private String complicity;
    private boolean isDone;

    public ProjectTaskDTO() {
    }

    public static List<ProjectTaskDTO> convertToDTOList(List<ProjectTask> tasks) {
        List<ProjectTaskDTO> result = new LinkedList<>();
        for (ProjectTask p : tasks) {
            result.add(ProjectTaskDTO.apply(p));
        }
        Collections.sort(result);
        return result;
    }

    private static ProjectTaskDTO apply(ProjectTask p) {
        ProjectTaskDTO dto = new ProjectTaskDTO();
        dto.setId(p.getId());
        dto.setCreationTime(p.getCreationDate().format(DATE_TIME_FORMATTER_TASK));
        if (p.getUpdateDate() != null) dto.setUpdateTime(p.getUpdateDate().format(DATE_TIME_FORMATTER_TASK));
        dto.setPositionInProject(p.getPositionInProject());
        dto.taskName = p.getTask().getTaskName();
        dto.description = p.getTask().getDescription();
        dto.timeInMinutes = p.getTask().getTaskDetails().getTimeInMinutes();
        dto.imageUrl = p.getTask().getTaskDetails().getImageUrl();
        dto.price = p.getTask().getTaskDetails().getPrice();
        dto.category = p.getTask().getTaskDetails().getCategory().getCategoryName();
        dto.complicity = p.getTask().getTaskDetails().getComplicity().name();
        dto.isDone = p.isDone();
        return dto;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Integer getPositionInProject() {
        return positionInProject;
    }

    public void setPositionInProject(Integer positionInProject) {
        this.positionInProject = positionInProject;
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

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Integer timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComplicity() {
        return complicity;
    }

    public void setComplicity(String complicity) {
        this.complicity = complicity;
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

    @Override
    public int compareTo(ProjectTaskDTO o) {
        return this.getPositionInProject() - o.getPositionInProject();
    }
}
