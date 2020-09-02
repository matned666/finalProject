package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.validation.NoValidation;
import eu.mnrdesign.matned.final_project.validation.ValidationInController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {

    @NotNull(message = "The login cannot be empty")
    @Size(min = 5, message = "The login must be at least {min} signs long")
    @Pattern(
            regexp = ".{1,}@.{1,}[.].{2,3}",
            message = "It should be a valid email address"
    )
    private String login;

    @NotNull(message = "The password field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String password;

    @ValidationInController
    private String passwordConfirm;

    @Pattern(regexp = "[A-z]{0,}", message = "Name should contain only letters")
    private String firstName;

    @Pattern(regexp = "[A-z]{0,}", message = "Surname should contain only letters")
    private String lastName;

    @NoValidation
    private String street;

    @NoValidation
    private String zipCode;

    @NoValidation
    private String city;

    @NoValidation
    private String country;

    @ValidationInController
    private String birthDate;

    @Pattern(regexp = "([+][0-9]{9,})|([0-9]{7,})|", message = "Proper format - only numbers or '+' and numbers")
    private String phoneNumber;

    @NoValidation
    private boolean preferEmails;

    public RegistrationDTO() {
    }

    private RegistrationDTO(RTDOBuilder builder){
        firstName = builder.firstName;
        lastName = builder.lastName;
        street = builder.street;
        zipCode = builder.zipCode;
        city = builder.city;
        country = builder.country;
        birthDate = builder.birthDate;
        login = builder.login;
        password = builder.password;
        passwordConfirm = builder.passwordConfirm;
        phoneNumber = builder.phoneNumber;
        preferEmails = builder.preferEmails;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

    public static class RTDOBuilder {


        private String firstName;
        private String lastName;
        private String street;
        private String zipCode;
        private String city;
        private String country;
        private String birthDate;
        private String login;
        private String password;
        private String passwordConfirm;
        private String phoneNumber;
        private boolean preferEmails;

        public RTDOBuilder() {
        }

        public RTDOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public RTDOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RTDOBuilder street(String street) {
            this.street = street;
            return this;
        }

        public RTDOBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public RTDOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public RTDOBuilder country(String country) {
            this.country = country;
            return this;
        }

        public RTDOBuilder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public RTDOBuilder login(String login) {
            this.login = login;
            return this;
        }

        public RTDOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RTDOBuilder passwordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
            return this;
        }

        public RTDOBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public RTDOBuilder preferEmails(boolean preferEmails) {
            this.preferEmails = preferEmails;
            return this;
        }

        public RegistrationDTO build() {
            return new RegistrationDTO(this);
        }
    }

}
