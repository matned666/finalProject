package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.ProjectDTO;
import eu.mnrdesign.matned.final_project.dto.ProjectTaskDTO;
import eu.mnrdesign.matned.final_project.model.Project;
import eu.mnrdesign.matned.final_project.model.ProjectTask;
import eu.mnrdesign.matned.final_project.model.Task;
import eu.mnrdesign.matned.final_project.model.User;
import eu.mnrdesign.matned.final_project.repository.ProjectRepository;
import eu.mnrdesign.matned.final_project.repository.ProjectTaskRepository;
import eu.mnrdesign.matned.final_project.repository.TaskRepository;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static eu.mnrdesign.matned.final_project.config.WebSecurityConfig.ADMIN_ADMIN_PL;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectTaskRepository projectTaskRepository,
                          TaskRepository taskRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public Page<ProjectDTO> findAllProjects(Pageable pageable){
        return new PageImpl<>(
                ProjectDTO.convertToDTOList(projectRepository.findAll()),
                pageable,
                projectRepository.findAll(pageable).getTotalElements());
    }

    public Page<ProjectDTO> findAllUserProjects(Long userId, Pageable pageable){
        return new PageImpl<>(
                ProjectDTO.convertToDTOList(projectRepository.findByUser(userId)),
                pageable,
                projectRepository.findByUserId(userId, pageable).getTotalElements());
    }

    public Page<ProjectDTO> findAllUserProjects(String userLogin, Pageable pageable){
        if (!userLogin.equals(ADMIN_ADMIN_PL)) {
            Long userId = userRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("No such user")).getId();
            return new PageImpl<>(
                    ProjectDTO.convertToDTOList(projectRepository.findByUser(userId)),
                    pageable,
                    projectRepository.findByUserId(userId, pageable).getTotalElements());
        }else {
            return findAllProjects(pageable);
        }
    }

    public ProjectDTO findProjectById(Long id){
        return ProjectDTO.apply(projectRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Project has not been found.")));
    }

    public void createProject(String login, ProjectDTO projectDTO){

        User user;
        if (!login.equals(ADMIN_ADMIN_PL)) {
            user = userRepository
                    .findByLogin(login)
                    .orElseThrow(() -> new RuntimeException("User with login: " + login + " has not been found"));
        }else{
            user = new User();
            user.setLogin(ADMIN_ADMIN_PL);
        }
        projectRepository.save(Objects.requireNonNull(Project.apply(user, projectDTO)));
    }

    public void addTaskToProject(Long projectId, Long taskId){
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(()-> new RuntimeException("Task not found"));
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(()->new RuntimeException("No project found"));
        ProjectTask projectTask = new ProjectTask(project, task);
        project.getTasks().add(projectTask);
        projectRepository.save(project);

    }

    public void moveTaskUp(Long projectId, Long projectTaskId){
        moveTask(projectId, projectTaskId, 1);
    }

    public void moveTaskDown(Long projectId, Long projectTaskId){
        moveTask(projectId, projectTaskId, -1);
    }

    public void update(Long projectId, ProjectDTO projectDTO){
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(()->new RuntimeException("No project found"));
        project.setImageUrl(projectDTO.getImageUrl());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setUpdateDate(LocalDateTime.now());
        projectRepository.save(project);

    }

    public void deleteTaskFromProject(Long projectId, Long projectTaskId) {
        projectTaskRepository.deleteByPTId(projectTaskId);
        List<ProjectTask> projectTasks = projectTaskRepository.findAllByProjectId(projectId);
        replanPositions(projectTasks);

    }

    public Page<ProjectTaskDTO> findAllProjectTasksByProject(Long id, Pageable pageable) {
        return new PageImpl<>(ProjectTaskDTO.convertToDTOList(projectTaskRepository.findAllByProjectId(id)),
                pageable,
                projectTaskRepository.findAllByProjectId(id,pageable).getTotalElements());
    }

    public BigDecimal getTotalPrice(Long projectId) {
        List<BigDecimal> decimals = new LinkedList<>();
        projectTaskRepository.findAllByProjectId(projectId)
                .forEach(a->decimals.add(a.getTask().getTaskDetails().getPrice()));
        return decimals.stream()
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    public Integer getTotalTime(Long projectId) {
        List<Integer> integers = new LinkedList<>();
        projectTaskRepository.findAllByProjectId(projectId)
                .forEach(a->integers.add(a.getTask().getTaskDetails().getTimeInMinutes()));
        return integers.stream()
                .reduce(0, Integer::sum);    }

    public void updateProjectTask(ProjectTask projectTask){
        projectTaskRepository.save(projectTask);
    }

    public void removeProject(Long projectId) {
        Project projectToDelete = projectRepository.findById(projectId)
                .orElseThrow(()->new RuntimeException("No such project"));
        projectRepository.delete(projectToDelete);
    }

    public void clearProject(Long projectId) {
        List<ProjectTask> tasksToDelete = projectTaskRepository.findAllByProjectId(projectId);
        tasksToDelete.forEach(projectTaskRepository::delete);
    }

    private void replanPositions(List<ProjectTask> projectTasks) {
        Collections.sort(projectTasks);
        for (int i = 0; i < projectTasks.size(); i++) {
            ProjectTask pt = projectTasks.get(i);
            pt.setPositionInProject(i+1);
            updateProjectTask(pt);
        }
    }

    private void moveTask(Long projectId, Long projectTaskId, int positionsToMove){
        List<ProjectTask> list = projectTaskRepository.findAllByProjectId(projectId);
        ProjectTask movedTask = list.stream()
                .filter(v -> v.getId().equals(projectTaskId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No such project task"));
        Integer movedPosition = movedTask.getPositionInProject();
        ProjectTask replacedTask = list.stream()
                .filter(v -> v.getPositionInProject().equals(movedPosition+positionsToMove))
                .findFirst()
                .orElse(null);
        if (replacedTask != null) {
            Integer replacedPosition = replacedTask.getPositionInProject();
            movedTask.setPositionInProject(replacedPosition);
            replacedTask.setPositionInProject(movedPosition);
            updateProjectTask(movedTask);
            updateProjectTask(replacedTask);
        }
    }
}
