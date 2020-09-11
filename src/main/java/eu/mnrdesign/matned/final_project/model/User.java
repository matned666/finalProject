package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_PATTERN;

@Entity
@Table(name = "USER_ENTITY")
public class User extends BaseEntity {


    private String firstName;
    private String lastName;
    @Embedded
    private Address address;
    private LocalDate birthDate;
    private String login;
    private String password;
    private String phoneNumber;
    private boolean preferEmails;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<UserRole> roles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Project> projects;

    public User() {
    }

    public static User apply(RegistrationDTO registrationDTO, String passwordHash) {
        User user = new User();
        user.firstName = registrationDTO.getFirstName();
        user.lastName = registrationDTO.getLastName();
        user.address = Address.apply(registrationDTO);
        try {
            user.birthDate = LocalDate.parse(registrationDTO.getBirthDate(), DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (DateTimeParseException e) {
            user.birthDate = null;
        }
        user.login = registrationDTO.getLogin();
        user.password = passwordHash;
        user.phoneNumber = registrationDTO.getPhoneNumber();
        user.preferEmails = registrationDTO.isPreferEmails();
        return user;
    }

    public void addRole(UserRole userRole){
        if(isRoleCorrect(userRole)) return;
        if (roles == null) roles = new ArrayList<>();
        roles.add(userRole);
    }

    private boolean isRoleCorrect(UserRole userRole) {
        return roles != null && roles.stream().anyMatch(r -> r.getRoleName().equals(userRole.getRoleName()));
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public eu.mnrdesign.matned.final_project.model.Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPreferEmails() {
        return preferEmails;
    }

    public void setPreferEmails(boolean preferEmails) {
        this.preferEmails = preferEmails;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

}
