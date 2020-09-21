package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.BasketItemDTO;
import eu.mnrdesign.matned.final_project.model.Project;
import eu.mnrdesign.matned.final_project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SessionScope
public class BasketService {
    private final ProjectRepository projectRepository;

    private final Map<Long, BasketItemDTO> projects = new HashMap<>();


    public BasketService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void addProjectToBasket(Long id) {
        Project projectById = projectRepository.findById(id).orElseThrow(()->new RuntimeException("No such project in database"));
        BasketItemDTO basketItemDto = new BasketItemDTO(projectById, 1);
        if (projects.containsKey(id)) {
            BasketItemDTO basketItem = projects.get(id);
            Integer amount = basketItem.getAmount();
            basketItem.setAmount(amount + 1);
        } else {
            projects.put(id, basketItemDto);
        }
    }

    public void removeProjectFromBasket(Long id) {
        if (projects.containsKey(id)) {
            BasketItemDTO basketItem = projects.get(id);
            Integer amount = basketItem.getAmount();
            basketItem.setAmount(amount - 1);
            if (basketItem.getAmount() <= 0)
                projects.remove(id);
        } else {
            throw new RuntimeException("No such item in the basket");
        }
    }

    public void clearBasket(){
        projects.clear();
    }

    public void changeAmountProjectsInBasket(Integer amount, Long id){
        if (amount > 0) {
            if (projects.containsKey(id)) {
                BasketItemDTO basketItem = projects.get(id);
                basketItem.setAmount(amount);
            } else {
                throw new RuntimeException("No such item in the basket");
            }
        } else projects.remove(id);
    }

    public List<BasketItemDTO> getProjects() {
        return new ArrayList<>(projects.values());
    }
}
