package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private String roleName;

    public UserService(UserRepository repo, UserRoleRepository userRoleRepository) {
        this.repo = repo;
        this.userRoleRepository = userRoleRepository;
    }

    public void register(RegistrationDTO registrationDTO){
        String passwordEncoded = passwordEncoder.encode(registrationDTO.getPassword());
        String login = registrationDTO.getLogin();
        if (userWithEmailExists(login))
            throw new RuntimeException("User with "+ login + " exists.");

        User userToSave = User.apply(registrationDTO, passwordEncoded);
        roleName = "ROLE_"+UserRole.Role.USER.name();
        userToSave.addRole(userRoleRepository.findByRoleName(roleName));
        repo.save(userToSave);
    }

    public User findByLogin(String login) {return repo.findByLogin(login).orElse(null);}

    public List<User> findAll() {return repo.findAll();}

    private boolean userWithEmailExists(String login) {
        return repo.existsByLogin(login);
    }

    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
