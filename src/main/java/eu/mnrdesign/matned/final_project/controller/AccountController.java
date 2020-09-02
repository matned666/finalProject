package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.config.WebSecurityConfig;
import eu.mnrdesign.matned.final_project.holder.AccountHolder;
import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static eu.mnrdesign.matned.final_project.holder.SecurityHolder.actualUserName;

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
            AccountHolder.getInstance().setSelectedAccountId(actualUser.getId());
        }
        return "account";
    }

    @GetMapping("/account/{id}")
    public String accountShow(@PathVariable Long id, Model model) {
        AccountHolder.getInstance().setSelectedAccountId(id);
        User user = userService.findById(id);
        model.addAttribute("userToSee", user);
        return "account";
    }

    @GetMapping("/edit-user/{id}")
    public String accountEditionPage(@PathVariable Long id, Model model){
        User user = userService.findById(id);
        AccountHolder.getInstance().setSelectedAccountId(id);
        model.addAttribute("Edit", "Edit");
        model.addAttribute("editedUser", user);
        return "account";
    }

    @PostMapping("/edit-process")
    public String editUserProcess(@RequestBody RegistrationDTO registrationDTO, Model model){
        Long id = AccountHolder.getInstance().getSelectedAccountId();
        userService.update(id, registrationDTO);
        User user = userService.findById(id);
        model.addAttribute("userToSee", user);
        return "account";
    }


}
