package eu.mnrdesign.matned.final_project.model;

import javax.persistence.Entity;
import java.util.Objects;


@Entity
public class UserRole extends BaseEntity{

    private String roleName;

    public UserRole() {
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(roleName, userRole.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roleName);
    }
}
