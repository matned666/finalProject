package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;
    private String passwordEncoded;

    public UserService(UserRepository repo, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegistrationDTO registrationDTO){
        passwordEncoded = passwordEncoder.encode(registrationDTO.getPassword());
        User userToSave = getUser(registrationDTO);
        return repo.save(userToSave);
    }

    private User getUser(RegistrationDTO registrationDTO) {
        User userToSave = User.apply(registrationDTO, passwordEncoded);
        String roleName = "ROLE_" + UserRole.Role.USER.name();
        userToSave.addRole(userRoleRepository.findByRoleName(roleName));
        return userToSave;
    }

    public User findByLogin(String login) {return repo.findByLogin(login).orElse(null);}

    public List<User> findAll() {return repo.findAll();}

    public boolean userWithEmailExists(String login) {
        return repo.existsByLogin(login);
    }

    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public RegistrationDTO createDTO(User user){
        return new RegistrationDTO.RTDOBuilder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .street(user.getAddress().getStreet())
                .city(user.getAddress().getCity())
                .zipCode(user.getAddress().getZipCode())
                .country(user.getAddress().getCountry().name())
                .birthDate(user.getBirthDate().toString())
                .phoneNumber(user.getPhoneNumber())
                .preferEmails(user.isPreferEmails())
                .build();
    }

    public void update(Long id, RegistrationDTO registrationDTO) {
        passwordEncoded = passwordEncoder.encode(registrationDTO.getPassword());
        User user = User.apply(registrationDTO, passwordEncoded);
        user.setId(id);
        repo.save(user);
    }

}
