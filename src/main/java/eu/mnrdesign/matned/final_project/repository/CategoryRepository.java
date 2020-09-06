package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("select case when count (ct) > 0 then true else false end from Category ct where lower(ct.categoryName) like lower(?1) ")
    boolean existsByCategoryName(String categoryName);

    @Query("select ct from Category ct where lower(ct.categoryName) = lower(?1)")
    Category findByName(String category);
}
