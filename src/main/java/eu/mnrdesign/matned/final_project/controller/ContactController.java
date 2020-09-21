package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.message.Message;
import eu.mnrdesign.matned.final_project.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final MessageService messageService;

    public ContactController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/contact-form")
    public String contactInfo(Model model){
        model.addAttribute("message", new Message());
        return "contact";
    }

    @PostMapping("/contact-form-post")
    public String contactInfoPost(@Validated Message message,BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("err", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("message", message);
            return "contact";
        }
        messageService.send(message);
        message = new Message();
        model.addAttribute("message", message);
        model.addAttribute("sent", 1);
        return "contact";
    }



}
