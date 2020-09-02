package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.Countries;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.model.UserRole;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import eu.mnrdesign.matned.final_project.validation.DateValidatorUsingDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_PATTERN;

@Service
public class UserService {

    private final UserRepository repo;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private String roleName;
    private String passwordEncoded;

    public UserService(UserRepository repo, UserRoleRepository userRoleRepository) {
        this.repo = repo;
        this.userRoleRepository = userRoleRepository;
    }

    public User register(RegistrationDTO registrationDTO){
        passwordEncoded = passwordEncoder.encode(registrationDTO.getPassword());
        String login = registrationDTO.getLogin();
        User userToSave = getUser(registrationDTO);
        return repo.save(userToSave);
    }

    private User getUser(RegistrationDTO registrationDTO) {
        User userToSave = User.apply(registrationDTO, passwordEncoded);
        roleName = "ROLE_"+UserRole.Role.USER.name();
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

    public String userValidRegistrationRedirect(RegistrationDTO registrationDTO, BindingResult bindingResult, Model model) {
        boolean isDateOK = new DateValidatorUsingDateFormat(DateTimeFormatter.ofPattern(DATE_PATTERN))
                .isValid(registrationDTO.getBirthDate());
        boolean arePasswordsEqual = registrationDTO.getPassword().equals(registrationDTO.getPasswordConfirm());
        boolean isUserExisting = userWithEmailExists(registrationDTO.getLogin());
        if(bindingResult.hasErrors() || !isDateOK || !arePasswordsEqual || isUserExisting){
            model.addAttribute("error", "error");
            if (isUserExisting) model.addAttribute("userExists", "A user with the same login exists.");
            else model.addAttribute("userExists", "no");
            if (!isDateOK) model.addAttribute("dateError", "Wrong date format - should be yyyy-MM-dd");
            else model.addAttribute("dateError", "no");
            if (!arePasswordsEqual) model.addAttribute("passwordError", "The passwords should be equal");
            else model.addAttribute("passwordError", "no");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("registrationObject", registrationDTO);
            return "registrationPage";
        }
        register(registrationDTO);
        return "redirect:/login";
    }
}
