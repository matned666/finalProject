package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.config.WebSecurityConfig;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.stream.Collectors;

import static eu.mnrdesign.matned.final_project.data_access.SecurityAccess.actualUserName;

@Controller
public class HomePageController {

    private final UserService userService;

    public HomePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String openStartPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        return "index";
    }
}
