package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.TaskDTO;
import eu.mnrdesign.matned.final_project.model.Category;
import eu.mnrdesign.matned.final_project.model.Complicity;
import eu.mnrdesign.matned.final_project.model.Task;
import eu.mnrdesign.matned.final_project.repository.CategoryRepository;
import eu.mnrdesign.matned.final_project.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_TIME_FORMATTER_TASK;

@Service
public class TaskService {

    private final TaskRepository repo;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository repo, CategoryRepository categoryRepository) {
        this.repo = repo;
        this.categoryRepository = categoryRepository;
    }

    public void add(TaskDTO taskDTO) {
        Task taskToSave = Task.apply(taskDTO);
        taskToSave.getTaskDetails().setCategory(categoryRepository.findByName(taskDTO.getCategory()));
        repo.save(taskToSave);
    }

    public Page<TaskDTO> findAll(Pageable pageable) {
        return convertList(repo.findAll(pageable), pageable);

    }

    private Page<TaskDTO> convertList(Page<Task> all, Pageable pageable) {
        List<TaskDTO> taskDTOS = new LinkedList<>();
        for (Task task : all) {
            taskDTOS.add(convert(task));
        }
        return new PageImpl<>(taskDTOS, pageable, all.getTotalElements());
    }

    public void update(Long id, TaskDTO taskDTO) {
        Task task = repo.findById(id).orElse(null);
        if (task != null) {
            task.setTaskName(taskDTO.getTaskName());
            task.setDescription(taskDTO.getDescription());
            task.getTaskDetails().setCategory(categoryRepository.findByName(taskDTO.getCategory()));
            task.getTaskDetails().setPrice(taskDTO.getPrice());
            task.getTaskDetails().setTimeInMinutes(taskDTO.getTimeInMinutes());
            task.getTaskDetails().setImageUrl(taskDTO.getImageUrl());
            task.getTaskDetails().setComplicity(Complicity.valueOf(taskDTO.getComplicity()));
            task.setUpdateDate(LocalDateTime.now());
            task.setVersion(task.getVersion() + 1);
            repo.save(task);
        }
    }

    public TaskDTO findById(Long id) {
        return convert(Objects.requireNonNull(repo.findById(id).orElse(null)));
    }

    private TaskDTO convert(Task task) {
        return TaskDTO.apply(task);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
