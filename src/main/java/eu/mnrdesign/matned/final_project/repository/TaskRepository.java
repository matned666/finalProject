package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {



}
