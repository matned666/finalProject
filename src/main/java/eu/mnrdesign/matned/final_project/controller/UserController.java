package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.config.WebSecurityConfig;
import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.dto.UserDTO;
import eu.mnrdesign.matned.final_project.holder.AccountHolder;
import eu.mnrdesign.matned.final_project.model.Countries;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static eu.mnrdesign.matned.final_project.holder.SecurityHolder.actualUserName;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String getRegistration( Model model){
        RegistrationDTO registrationDTO = new RegistrationDTO();
        model.addAttribute("countries", Countries.values());
        model.addAttribute("registrationObject", registrationDTO);
        return "registrationPage";
    }

    @PostMapping("/register")
    public String postRegistration(@Validated RegistrationDTO registrationDTO,
                                   BindingResult bindingResult,
                                   Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("registrationObject", registrationDTO);
            return "registrationPage";
        }
        service.register(registrationDTO);
        return "redirect:/login";
    }



    @GetMapping("/users-list")
    public String getAllUsersList(Model model){
        model.addAttribute("all_users", service.findAll());
        return "users-list";
    }

    @GetMapping("/account")
    public String accountShow(Model model) {
        String actualUserLogin = actualUserName();
        if (!actualUserLogin.equals(WebSecurityConfig.ADMIN_ADMIN_PL)) {
            User actualUser = service.findByLogin(actualUserLogin);
            model.addAttribute("user", actualUser);
            AccountHolder.getInstance().setSelectedAccountId(actualUser.getId());
        }
        return "account";
    }

    @GetMapping("/account/{id}")
    public String accountShow(@PathVariable Long id, Model model) {
        AccountHolder.getInstance().setSelectedAccountId(id);
        RegistrationDTO user = service.findById(id);
        model.addAttribute("userToSee", user);
        return "account";
    }

    @GetMapping("/user/edit/{id}")
    public String accountEditionPage(@PathVariable Long id, Model model){
        UserDTO user = service.findById(id, true);
        model.addAttribute("editedUser", user);
        return "edit-user";
    }

    @PostMapping("/user/edit/{id}")
    public String editUserProcess(@PathVariable Long id,
                                  @Validated UserDTO userDTO,
                                  BindingResult bindingResult,
                                  Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("registrationObject", userDTO);
            return "edit-user";
        }
        service.update(id, userDTO);
        return "redirect:/account/"+id;
    }

    @GetMapping("/user/delete/{id}")
    public String accountDelete(@PathVariable Long id, Model model){
        service.delete(id);
        return "redirect:/users-list";
    }


}
