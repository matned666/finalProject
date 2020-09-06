package eu.mnrdesign.matned.final_project;

import eu.mnrdesign.matned.final_project.model.Category;
import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.CategoryRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSeed implements InitializingBean {

    private final UserRoleRepository roleRepository;

    private final CategoryRepository categoryRepository;

    public DataSeed(UserRoleRepository roleRepository, CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void afterPropertiesSet() {
        for (UserRole.Role role: UserRole.Role.values()) {
            createRole(role);
        }
        for (Category.DefaultCategory defCat: Category.DefaultCategory.values()) {
            createCat(defCat);
        }
    }

    private void createRole(UserRole.Role role) {
        String roleCheck = "ROLE_" + role.name();
        if (!roleRepository.roleExists(roleCheck)){
            roleRepository.save(UserRole.apply(role));
        }
    }

    private void createCat(Category.DefaultCategory defCat) {
        if (!categoryRepository.existsByCategoryName(defCat.name())){
            categoryRepository.save(new Category(defCat.name()));
        }
    }

}
