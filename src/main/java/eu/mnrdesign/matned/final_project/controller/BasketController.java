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
        model.addAttribute("totalAmount", basketService.getTotalAmount());
        return "basket";
    }

    @GetMapping("/basket-deleteItem/{id}")
    public String deleteItem(@PathVariable Long id){
        basketService.removeProjectFromBasket(id);
        return "redirect:/basket";
    }

    @GetMapping("/basket-change-item-amount/{id}")
    public String changeItemAmount(@PathVariable Long id, Integer amount){
        basketService.changeAmountProjectsInBasket(amount, id);
        return "redirect:/basket";
    }

    @GetMapping("/basket-increase-item-amount/{id}")
    public String increaseItemAmount(@PathVariable Long id, Integer amount){
        basketService.increaseAmountProjectsInBasket(amount, id);
        return "redirect:/basket";
    }

    @GetMapping("/basket-clear")
    public String clearBasket(){
        basketService.clearBasket();
        return "redirect:/basket";
    }


}
