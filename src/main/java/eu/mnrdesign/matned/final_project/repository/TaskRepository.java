package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

//    @Query("delete from Task t where t.id = ?1")
    void deleteById(Long id);


}
