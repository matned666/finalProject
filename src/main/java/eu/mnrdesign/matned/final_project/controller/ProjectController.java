package eu.mnrdesign.matned.final_project.controller;

import eu.mnrdesign.matned.final_project.dto.ProjectDTO;
import eu.mnrdesign.matned.final_project.service.ProjectService;
import eu.mnrdesign.matned.final_project.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService,
                             TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/projects")
    public String getProjectsList(Model model,
                                  Principal principal,
                                  Pageable pageable){
        Page<ProjectDTO> projects = projectService.findAllUserProjects(principal.getName(), pageable);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/projects/add")
    public String getAddProject(Model model){
        model.addAttribute("project", new ProjectDTO());
        return "add_project";
    }

    @PostMapping("/projects/add")
    public String postAddProject(@Validated ProjectDTO projectDTO,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("project", projectDTO);
            return "add_project";
        }
        projectService.createProject(principal.getName(), projectDTO);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}")
    public String getChosenProject(@PathVariable Long id,
                                   Model model,
                                   Pageable pageable){
        ProjectDTO projectDTO = projectService.findProjectById(id);
        model.addAttribute("projectId", id);
        model.addAttribute("totalTime", projectService.getTotalTime(id));
        model.addAttribute("totalPrice", projectService.getTotalPrice(id));
        model.addAttribute("availableTasks", taskService.findAll(pageable));
        model.addAttribute("projectTasks", projectService.findAllProjectTasksByProject(id, pageable));
        model.addAttribute("project", projectDTO);
        return "project";
    }

    @GetMapping("/projects/{projectId}/add/{taskId}")
    public String postAddTaskToProject(@PathVariable Long projectId,
                                       @PathVariable Long taskId){
        projectService.addTaskToProject(projectId, taskId);
        return "redirect:/projects/"+projectId;
    }

    @GetMapping("/projects/{projectId}/delete/{projectTaskId}")
    public String postDeleteTaskFromProject(@PathVariable Long projectId,
                                            @PathVariable Long projectTaskId){
        projectService.deleteTaskFromProject(projectId, projectTaskId);
        return "redirect:/projects/"+projectId;
    }

    @GetMapping("/projects/{projectId}/move-task-up/{taskId}")
    public String moveTaskUpInProject(@PathVariable Long projectId,
                                      @PathVariable Long taskId) {
        projectService.moveTaskUp(projectId, taskId);
        return "redirect:/projects/"+projectId;
    }

    @GetMapping("/projects/{projectId}/move-task-down/{taskId}")
    public String moveTaskDownInProject(@PathVariable Long projectId,
                                        @PathVariable Long taskId){
        projectService.moveTaskDown(projectId, taskId);
        return "redirect:/projects/"+projectId;
    }

    @GetMapping("/projects/{projectId}/rename")
    public String getEditProjectData(@PathVariable Long projectId,
                                     Model model){
        model.addAttribute("project", projectService.findProjectById(projectId));
        return "project-edit";
    }

    @PostMapping("/projects/{projectId}/rename")
    public String postEditProjectData(@Validated ProjectDTO projectDTO,
                                      @PathVariable Long projectId,
                                      BindingResult bindingResult,
                                      Principal principal,
                                      Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("project", projectDTO);
            return "project-edit";
        }
        projectService.update(projectId, projectDTO);
        return "redirect:/projects/"+projectId;
    }

    @GetMapping("/projects/{projectId}/delete")
    public String projectDelete(@PathVariable Long projectId){
        projectService.removeProject(projectId);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{projectId}/clear")
    public String projectClear(@PathVariable Long projectId){
        projectService.clearProject(projectId);
        return "redirect:/projects/"+projectId;
    }




}
