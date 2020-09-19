package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.dto.PasswordDTO;
import eu.mnrdesign.matned.final_project.dto.PasswordResetDTO;
import eu.mnrdesign.matned.final_project.service.MessageService;
import eu.mnrdesign.matned.final_project.service.PasswordService;
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

    private final MessageService messageService;
    private final PasswordService passWordService;

    public PasswordController(MessageService messageService,
                              PasswordService passWordService) {
        this.messageService = messageService;
        this.passWordService = passWordService;
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


    @GetMapping("/token/{token}")
    public String changePasswordGet(@PathVariable String token, Model model){
        PasswordDTO passwordDTO = passWordService.findByToken(token);
        if(passwordDTO == null) {
            model.addAttribute("tokenError", 1);
            return "loginPage";
        }
        model.addAttribute("passwordDTO",passwordDTO);
        return "change-password";
    }


    @PostMapping("/token/{token}")
    public String changePasswordPost(@PathVariable String token, @Validated PasswordDTO passwordDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("err", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("passwordDTO", passwordDTO);
            return "change-password";
        }
        model.addAttribute("passwordSet", 1);
        passWordService.setPassword(passwordDTO, token);
        return "loginPage";
    }

}
