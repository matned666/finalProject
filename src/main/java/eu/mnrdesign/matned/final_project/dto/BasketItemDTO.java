package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.Project;

import java.util.Objects;

public class BasketItemDTO {

    private Project project;
    private Integer amount;

    public BasketItemDTO(Project project, Integer amount) {
        this.project = project;
        this.amount = amount;
    }

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketItemDTO that = (BasketItemDTO) o;
        return project.equals(that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }
}
