package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p where p.user.id = ?1")
    List<Project> findByUser(Long userId);

    @Query("select p from Project p where p.user.id = ?1")
    Page<Project> findByUserId(Long userId, Pageable pageable);
}
