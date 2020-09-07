package eu.mnrdesign.matned.final_project.dto;


public interface UserDTOInterface<E> {

    Long getId();
    String getFirstName();
    String getLastName();
    String getStreet();
    String getZipCode();
    String getCity();
    String getCountry();
    String getBirthDate();
    String getPhoneNumber();
    boolean isPreferEmails();
}
