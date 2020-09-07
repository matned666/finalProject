package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.config.WebSecurityConfig;
import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.dto.RestrictedRegistrationDTO;
import eu.mnrdesign.matned.final_project.dto.UserDTOInterface;
import eu.mnrdesign.matned.final_project.holder.AccountHolder;
import eu.mnrdesign.matned.final_project.model.Countries;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String getRegistration(Model model) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        model.addAttribute("countries", Countries.values());
        model.addAttribute("registrationObject", registrationDTO);
        return "registrationPage";
    }

    @PostMapping("/register")
    public String postRegistration(@Validated RegistrationDTO registrationDTO,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
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
    public String getAllUsersList(Model model, HttpServletRequest request) {
        if (request.isUserInRole(WebSecurityConfig.ROLE_ADMIN)) {
            model.addAttribute("all_users", service.findAll());
            return "users-list";
        } else return "accessDenied";
    }

    @GetMapping("/account")
    public String accountShow(Model model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN)) {
            RestrictedRegistrationDTO actualUser = service.findByLoginRestricted(principal.getName());
            model.addAttribute("userToSee", actualUser);
            AccountHolder.getInstance().setSelectedAccountId(actualUser.getId());
            return "account";
        } else {
            return "redirect:/users-list";
        }
    }

    @GetMapping("/account/{id}")
    public String accountShow(@PathVariable Long id,
                              Model model,
                              HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN)) {
            return "accessDenied";
        } else {
            AccountHolder.getInstance().setSelectedAccountId(id);
            RegistrationDTO user = service.findById(id);
            model.addAttribute("userToSee", user);
            return "account";
        }
    }

    @GetMapping("/user/edit/{id}")
    public String accountEditionPage(@PathVariable Long id,
                                     Model model,
                                     HttpServletRequest request) {
        UserDTOInterface<?> user;
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN)) {
            return "accessDenied";
        } else {
            user = service.findById(id);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("editedUser", user);
            return "edit-user";
        }

    }

    @GetMapping("/user/edit-user")
    public String accountEditionPageForActualCommonUser(Model model,
                                                        Principal principal) {
        UserDTOInterface<?> user = service.findByLogin(principal.getName());
        model.addAttribute("countries", Countries.values());
        model.addAttribute("editedUser", user);
        return "edit-user";

    }

    @PostMapping("/user/edit-user")
    public String accountEditionPostPageForActualCommonUser(@Validated RestrictedRegistrationDTO restrictedRegistrationDTO,
                                                            BindingResult bindingResult,
                                                            Model model,
                                                            Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("registrationObject", restrictedRegistrationDTO);
            return "edit-user";
        }
        service.updateByLogin(principal.getName(), restrictedRegistrationDTO);
        return "redirect:/account";

    }

    @PostMapping("/user/edit/{id}")
    public String editUserProcess(@PathVariable Long id,
                                  @Validated RestrictedRegistrationDTO restrictedRegistrationDTO,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN)) {
            return "accessDenied";
        } else {
            if (bindingResult.hasErrors()) {
                model.addAttribute("error", "error");
                model.addAttribute("binding", bindingResult);
                model.addAttribute("countries", Countries.values());
                model.addAttribute("registrationObject", restrictedRegistrationDTO);
                return "edit-user";
            }
            service.update(id, restrictedRegistrationDTO);
            return "redirect:/account/" + id;
        }
    }

    @GetMapping("/user/delete/{id}")
    public String accountDelete(@PathVariable Long id,
                                HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN)) {
            return "accessDenied";
        } else {
            service.delete(id);
            return "redirect:/users-list";
        }
    }


}
