package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.Countries;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        return service.userValidRegistrationRedirect(registrationDTO, bindingResult, model);
    }



    @GetMapping("/users-list")
    public String getAllUsersList(Model model){
        model.addAttribute("all_users", service.findAll());
        return "users-list";
    }
}
