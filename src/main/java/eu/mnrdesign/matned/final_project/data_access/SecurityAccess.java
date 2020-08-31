package eu.mnrdesign.matned.final_project.data_access;

import eu.mnrdesign.matned.final_project.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class SecurityAccess {

    public static String actualUserName() {
        String username;
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal instanceof User) {
            username = ((User)principal).getLogin();
        } else {
            username = principal.getName();
        }
        return username;
    }
}
