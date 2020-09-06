package eu.mnrdesign.matned.final_project.dto;


import eu.mnrdesign.matned.final_project.model.Category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_TIME_FORMATTER_TASK;

public class CategoryDTO {

    private Long categoryId;
    private Long version;
    @NotNull(message = "Category name cannot be empty")
    @Size(min = 2, max = 30, message = "Category nam should be between {min} and {max} signs long.")
    private String  categoryName;
    private String creationDate;
    private String updateDate;

    public CategoryDTO() {
    }

    public CategoryDTO(Long categoryId, Long version, String categoryName, String creationDate, String updateDate) {
        this.categoryId = categoryId;
        this.version = version;
        this.categoryName = categoryName;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public static CategoryDTO apply(Category category){
        return new CategoryDTO(
                category.getId(),
                category.getVersion(),
                category.getCategoryName(),
                category.getCreationDate().format(DATE_TIME_FORMATTER_TASK),
                category.getUpdateDate().format(DATE_TIME_FORMATTER_TASK)
        );

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", version=" + version +
                ", categoryName='" + categoryName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
