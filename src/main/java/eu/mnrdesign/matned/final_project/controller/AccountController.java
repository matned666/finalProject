package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.config.WebSecurityConfig;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static eu.mnrdesign.matned.final_project.data_access.SecurityAccess.actualUserName;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String accountShow(Model model) {
        String actualUserLogin = actualUserName();
        if (!actualUserLogin.equals(WebSecurityConfig.ADMIN_ADMIN_PL)) {
            User actualUser = userService.findByLogin(actualUserLogin);
            model.addAttribute("user", actualUser);
        }
        return "account";
    }

    @GetMapping("/account/{id}")
    public String accountShow(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("userToSee", user);
        return "account";
    }

}
