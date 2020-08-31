package eu.mnrdesign.matned.final_project.model;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {


    private String firstName;
    private String lastName;
    @Embedded
    private Address address;
    private LocalDate birthDate;
    private String pesel;
    private String login;
    private String password;
    private String phoneNumber;
    private boolean preferEmails;
    @ManyToMany
    private List<UserRole> roles;

    public User() {
    }

    public static User apply(RegistrationDTO registrationDTO, String passwordHash) {
        User user = new User();
        user.firstName = registrationDTO.getFirstName();
        user.lastName = registrationDTO.getLastName();
        user.address = Address.apply(registrationDTO);
        user.birthDate = LocalDate.parse(registrationDTO.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.pesel = registrationDTO.getPesel();
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
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
