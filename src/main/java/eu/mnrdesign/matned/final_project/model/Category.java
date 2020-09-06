package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.CategoryDTO;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Category extends BaseEntity{

    String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
        super.setCreationDate(LocalDateTime.now());
        version = 1L;
    }

    public static Category apply(CategoryDTO category) {
        return new Category(category.getCategoryName());
    }

    public String getCategoryName() {
        return categoryName;
    }

    public enum DefaultCategory{

        STANDARD_BRAIN_TASK("Standard brain task"),
        STANDARD_MOVEMENT_TASK("Standard movement task");

        private final String taskCategoryName;

        DefaultCategory(String taskCategoryName) {
            this.taskCategoryName = taskCategoryName;
        }

        public String getTaskCategoryName() {
            return taskCategoryName;
        }
    }
}
