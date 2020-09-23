package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.Project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class BasketItemDTO implements Serializable {

    private Project project;
    private Integer amount;
    private BigDecimal totalPrice;

    public BasketItemDTO(Project project, Integer amount) {
        this.project = project;
        this.amount = amount;
        totalPrice = project.getProjectCost().multiply(BigDecimal.valueOf(amount));
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
