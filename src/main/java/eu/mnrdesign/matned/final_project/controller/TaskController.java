package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.config.WebSecurityConfig;
import eu.mnrdesign.matned.final_project.dto.TaskDTO;
import eu.mnrdesign.matned.final_project.model.Complicity;
import eu.mnrdesign.matned.final_project.service.CategoryService;
import eu.mnrdesign.matned.final_project.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TaskController {


    private final TaskService service;
    private final CategoryService categoryService;

    public TaskController(TaskService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }


    @GetMapping("/tasks")
    public String getTasks(Pageable pageable, Model model) {
        model.addAttribute("tasks", service.findAll(pageable));
        return "tasks-list";
    }

    @GetMapping("/tasks/add")
    public String addProduct(Model model) {
        model.addAttribute("complicities", Complicity.values());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("new_task", new TaskDTO());
        return "add_task";
    }

    @PostMapping("/tasks/add")
    public String addProduct(@Validated TaskDTO taskDTO,
                             BindingResult bindingResult,
                             Model model) {
        model.addAttribute("new_task", new TaskDTO());
        if (errorHandler(bindingResult, model)) {
            model.addAttribute("new_task", taskDTO);
            return "add_task";
        }
        service.add(taskDTO);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String showTaskData(@PathVariable Long id, Model model) {
        TaskDTO dto = service.findById(id);
        model.addAttribute("task", dto);
        return "task";
    }

    @GetMapping("/task/edit/{id}")
    public String editTaskData(@PathVariable Long id,
                               Model model,
                               HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN))
        {
            return "accessDenied";
        }else {
            TaskDTO taskDTO = service.findById(id);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("complicities", Complicity.values());
            model.addAttribute("edited_task", taskDTO);
            return "edit_task";
        }
    }

    @PostMapping("/task/edit/{id}")
    public String editTaskDataPost(@PathVariable Long id,
                                   @Validated TaskDTO taskDTO,
                                   BindingResult bindingResult,
                                   Model model,
                                   HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN))
        {
            return "accessDenied";
        }else {
            if (errorHandler(bindingResult, model)) {
                model.addAttribute("edited_task", taskDTO);
                return "task";
            }
            service.update(id, taskDTO);
            return "redirect:/task/" + id;
        }
    }

    @GetMapping("/task/delete/{id}")
    public String editTaskDataPost(@PathVariable Long id, HttpServletRequest request) {
        if (!request.isUserInRole(WebSecurityConfig.ROLE_ADMIN))
        {
            return "accessDenied";
        }else {
            service.delete(id);
            return "redirect:/tasks";
        }
    }

    private boolean errorHandler(BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("complicities", Complicity.values());
            return true;
        }
        return false;
    }




}
