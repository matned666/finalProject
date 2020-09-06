package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.dto.UserDTO;
import eu.mnrdesign.matned.final_project.model.Countries;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_TIME_FORMATTER_BIRTHDAY;

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

    public RegistrationDTO findById(Long id) {
        return RegistrationDTO.apply(repo.findById(id).orElseThrow(() -> new RuntimeException("User with id: "+id+" has not been found")));
    }

    public UserDTO findById(Long id, boolean isUserDTO) {
        return UserDTO.apply(repo.findById(id).orElseThrow(() -> new RuntimeException("User with id: "+id+" has not been found")));
    }

    public void update(Long id, UserDTO userDTO) {
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User of "+id+" id has not been found"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getBirthDate() != null) if (!userDTO.getBirthDate().trim().equals(""))user.setBirthDate(LocalDate.parse(userDTO.getBirthDate(),DATE_TIME_FORMATTER_BIRTHDAY));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPreferEmails(userDTO.isPreferEmails());
        user.getAddress().setStreet(userDTO.getStreet());
        user.getAddress().setZipCode(userDTO.getZipCode());
        user.getAddress().setCity(userDTO.getCity());
        if (userDTO.getCountry() != null) if (!userDTO.getCountry().trim().equals("")) user.getAddress().setCountry(Countries.valueOf(userDTO.getCountry()));
        repo.save(user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
