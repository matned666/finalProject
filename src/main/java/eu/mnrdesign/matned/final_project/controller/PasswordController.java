package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.dto.PasswordResetDTO;
import eu.mnrdesign.matned.final_project.service.MessageService;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordController {

    private final UserService userService;
    private final MessageService messageService;

    public PasswordController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/resetPassword")
    public String resetPasswordGet(Model model){
        model.addAttribute("resetForm", new PasswordResetDTO());
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordPost(@Validated PasswordResetDTO passwordResetDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("err", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("resetForm", passwordResetDTO);
            return "resetPassword";
        }
        messageService.sendPasswordReset(passwordResetDTO);
        model.addAttribute("isTokenSent", 1);
        return "loginPage";
    }

    //    TODO


//    @GetMapping("/change-password/{token}")
//    public String changePasswordGet(@PathVariable String token, Model model){
//        model.addAttribute("token", token);
//        model.addAttribute("resetForm", new PasswordResetDTO());
//        return "change-password";
//    }

//    TODO

//    @PostMapping("/change-password/{token}")
//    public String changePasswordPost(@PathVariable String token, @Validated PasswordResetDTO passwordResetDTO, BindingResult bindingResult, Model model){
//        if (bindingResult.hasErrors()){
//            model.addAttribute("err", "error");
//            model.addAttribute("binding", bindingResult);
//            model.addAttribute("resetForm", passwordResetDTO);
//            return "change-password";
//        }
//        messageService.sendPasswordReset(passwordResetDTO);
//        return "redirect:/login";
//    }

}
