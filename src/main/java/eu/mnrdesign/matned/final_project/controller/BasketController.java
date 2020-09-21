package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.service.BasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/basket-add/{projectId}")
    public String getProjectBasketAdd(@PathVariable Long projectId, Model model){
        basketService.addProjectToBasket(projectId);
        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String getBasket(Model model){
        model.addAttribute("basketItems", basketService.getProjects());
        return "basket";
    }

}
