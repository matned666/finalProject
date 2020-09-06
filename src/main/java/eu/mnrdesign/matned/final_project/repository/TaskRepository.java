package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
    //
//    @Modifying
//    @Query("delete from Task as t where t.id =: task_id")
//    void delete(@Param("task_id") Long id);
    void deleteById(Long id);


}
