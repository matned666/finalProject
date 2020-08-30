package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void register(RegistrationDTO registrationDTO){
        String passwordHash = String.valueOf(registrationDTO.getPassword().hashCode());
        String login = registrationDTO.getLogin();
        if (userWithEmailExists(login))
            throw new RuntimeException("User with "+ login + " exists.");

        User userToSave = User.apply(registrationDTO, passwordHash);
        repo.save(userToSave);
    }

    private boolean userWithEmailExists(String login) {
        return repo.existsByLogin(login);
    }
}
