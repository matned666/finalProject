package eu.mnrdesign.matned.final_project;

import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSeed implements InitializingBean {

    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    public void afterPropertiesSet() {
        for (UserRole.Role role: UserRole.Role.values()) {
            createRole(role);
        }
    }

    private void createRole(UserRole.Role role) {
        String roleCheck = "ROLE_" + role.name();
        if (!roleRepository.roleExists(roleCheck)){
            roleRepository.save(UserRole.apply(role));
        }
    }

}
