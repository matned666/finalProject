package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.dto.TaskDTO;
import eu.mnrdesign.matned.final_project.model.Complicity;
import eu.mnrdesign.matned.final_project.service.CategoryService;
import eu.mnrdesign.matned.final_project.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TaskController {


    public static final int DEFAULT_START_PAGE = 0;
    public static final int DEFAULT_TASKS_PER_PAGE = 30;
    private final TaskService service;
    private final CategoryService categoryService;

    public TaskController(TaskService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }


    @GetMapping("/tasks")
    public String getTasks(Pageable pageable, Model model){
        model.addAttribute("tasks", service.findAll(pageable));
        return "tasks-list";
    }

    @GetMapping("/tasks/add")
    public String addProduct(Model model){
        model.addAttribute("complicities", Complicity.values());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("new_task", new TaskDTO());
        return "add_task";
    }

    @PostMapping("/tasks/add")
    public String addProduct(@Validated TaskDTO taskDTO,
                                   BindingResult bindingResult,
                                   Model model){
        model.addAttribute("new_task", new TaskDTO());
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("complicities", Complicity.values());
            model.addAttribute("new_task", taskDTO);
            return "add_task";
        }
        service.add(taskDTO);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String showTaskData(@PathVariable Long id, Model model){
        TaskDTO dto = service.findById(id);
        model.addAttribute("task", dto);
        return "task";
    }


}
