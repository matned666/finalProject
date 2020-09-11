package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.ProjectTask;
import eu.mnrdesign.matned.final_project.model.Task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_TIME_FORMATTER_TASK;

public class TaskDTO {

    @NotNull(message = "The field cannot be empty")
    @Size(min = 5, max = 30, message = "Write at between {min} and {max} signs")
    private String taskName;

    @NotNull(message = "The field cannot be empty")
    @Size(min = 5, max = 1000, message = "Write at between {min} and {max} signs")
    private String description;

    @NotNull(message = "The field cannot be empty")
    private Integer timeInMinutes;
    @Size(max = 1500, message = "Url can have max {max} signs")
    private String imageUrl;
    private BigDecimal price;
    private String category;
    private String complicity;

//       UNCHANGABLE ITEMS:
    private String creationTime;
    private String updateTime;
    private Long id;
    private Long authorId;

    public TaskDTO() {
    }

    private TaskDTO(TaskDTOBuilder builder) {
        taskName = builder.taskName;
        description = builder.description;
        timeInMinutes = builder.timeInMinutes;
        imageUrl = builder.imageUrl;
        price = builder.price;
        category = builder.category;
        complicity = builder.complicity;
    }

    public static TaskDTO apply(Task task){
        TaskDTO dto =  new TaskDTO.TaskDTOBuilder(task.getTaskName())
                .category(task.getTaskDetails().getCategory().getCategoryName())
                .price(task.getTaskDetails().getPrice())
                .complicity(task.getTaskDetails().getComplicity().name())
                .imageUrl(task.getTaskDetails().getImageUrl())
                .timeInMinutes(task.getTaskDetails().getTimeInMinutes())
                .description(task.getDescription())
                .build();
        if (task.getCreationDate() != null)
            dto.setCreationTime(task.getCreationDate().format(DATE_TIME_FORMATTER_TASK));
        if (task.getUpdateDate() != null)
            dto.setUpdateTime(task.getUpdateDate().format(DATE_TIME_FORMATTER_TASK));
        dto.setAuthorId(task.getTaskDetails().getAuthorId());
        dto.setId(task.getId());
        return dto;
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

    public String getCreationTime() {
        return creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", timeInMinutes=" + timeInMinutes +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", complicity='" + complicity + '\'' +
                '}';
    }

    public static class TaskDTOBuilder {

        private String taskName;
        private String description;
        private Integer timeInMinutes;
        private String imageUrl;
        private BigDecimal price;
        private String type;
        private String category;
        private String complicity;

        public TaskDTOBuilder(String taskName) {
            this.taskName = taskName;
        }

        public TaskDTOBuilder taskName(String taskName){
            this.taskName = taskName;
            return this;
        }

        public TaskDTOBuilder description(String description){
            this.description = description;
            return this;
        }

        public TaskDTOBuilder timeInMinutes(Integer timeInMinutes){
            this.timeInMinutes = timeInMinutes;
            return this;
        }

        public TaskDTOBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public TaskDTOBuilder price(BigDecimal price){
            this.price = price;
            return this;
        }

        public TaskDTOBuilder category(String category){
            this.category = category;
            return this;
        }

        public TaskDTOBuilder complicity(String complicity){
            this.complicity = complicity;
            return this;
        }

        public TaskDTO build(){
            return new TaskDTO(this);
        }


    }
}
