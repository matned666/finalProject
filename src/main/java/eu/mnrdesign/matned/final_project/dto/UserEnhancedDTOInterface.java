package eu.mnrdesign.matned.final_project.dto;

import eu.mnrdesign.matned.final_project.model.User;

public interface UserEnhancedDTOInterface<E> extends UserDTOInterface<E>{


    String getLogin();
    String getPassword();
    String getPasswordConfirm();

}
