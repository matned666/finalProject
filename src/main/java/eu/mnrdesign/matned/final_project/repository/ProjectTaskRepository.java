package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.ProjectTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    @Query("select pt from ProjectTask pt where pt.project.id = ?1")
    Page<ProjectTask> findAllByProjectId(Long projectId, Pageable pageable);

    @Query("select pt from ProjectTask pt where pt.project.id = ?1")
    List<ProjectTask> findAllByProjectId(Long projectId);

    @Transactional
    @Modifying
    @Query("delete from ProjectTask pt where pt.id = ?1")
    void deleteByPTId(Long projectTaskId);


}
