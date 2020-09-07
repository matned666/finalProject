package eu.mnrdesign.matned.final_project.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Embeddable
public class TaskDetails{;


    private Integer timeInMinutes;
    @Column(length = 1500)
    private String imageUrl;
    private BigDecimal price;
    @ManyToOne
    private Category category;
    @Enumerated
    private Complicity complicity;
    private Long authorId;
    @ManyToMany
    private List<User> usersAllowed;




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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Complicity getComplicity() {
        return complicity;
    }

    public void setComplicity(Complicity complicity) {
        this.complicity = complicity;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<User> getUsersAllowed() {
        return usersAllowed;
    }

    public void setUsersAllowed(List<User> usersAllowed) {
        this.usersAllowed = usersAllowed;
    }
}
