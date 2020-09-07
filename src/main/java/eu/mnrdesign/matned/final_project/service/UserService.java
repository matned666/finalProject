package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.dto.RestrictedRegistrationDTO;
import eu.mnrdesign.matned.final_project.model.Countries;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public RegistrationDTO findByLogin(String login) {
        return RegistrationDTO.apply(repo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login: "+login+" cannot be found.")));
    }

    public RestrictedRegistrationDTO findByLoginRestricted(String login) {
        return RestrictedRegistrationDTO.apply(repo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login: "+login+" cannot be found.")));
    }

    public List<User> findAll() {return repo.findAll();}

    public boolean userWithEmailExists(String login) {
        return repo.existsByLogin(login);
    }

    public RegistrationDTO findById(Long id) {
        return RegistrationDTO.apply(repo.findById(id).orElseThrow(() -> new RuntimeException("User with id: "+id+" has not been found")));
    }

    public RestrictedRegistrationDTO findByIdRestricted(Long id) {
        return RestrictedRegistrationDTO.apply(repo.findById(id).orElseThrow(() -> new RuntimeException("User with id: "+id+" has not been found")));
    }

    public void update(Long id, RestrictedRegistrationDTO restrictedRegistrationDTO) {
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User of "+id+" id has not been found"));
        updateUser(restrictedRegistrationDTO, user);
    }

    public void updateByLogin(String login, RestrictedRegistrationDTO restrictedRegistrationDTO) {
        User user = repo.findByLogin(login).orElseThrow(() -> new RuntimeException("User with login: "+login+" has not been found"));
        updateUser(restrictedRegistrationDTO, user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }




    private void updateUser(RestrictedRegistrationDTO restrictedRegistrationDTO, User user) {
        user.setFirstName(restrictedRegistrationDTO.getFirstName());
        user.setLastName(restrictedRegistrationDTO.getLastName());
        if (restrictedRegistrationDTO.getBirthDate() != null)
            if (!restrictedRegistrationDTO.getBirthDate().trim().equals(""))
                user.setBirthDate(LocalDate.parse(restrictedRegistrationDTO.getBirthDate(), DATE_TIME_FORMATTER_BIRTHDAY));
        user.setPhoneNumber(restrictedRegistrationDTO.getPhoneNumber());
        user.setPreferEmails(restrictedRegistrationDTO.isPreferEmails());
        user.getAddress().setStreet(restrictedRegistrationDTO.getStreet());
        user.getAddress().setZipCode(restrictedRegistrationDTO.getZipCode());
        user.getAddress().setCity(restrictedRegistrationDTO.getCity());
        if (restrictedRegistrationDTO.getCountry() != null)
            if (!restrictedRegistrationDTO.getCountry().trim().equals(""))
                user.getAddress().setCountry(Countries.fromSymbol(restrictedRegistrationDTO.getCountry()));
        repo.save(user);
    }


}
