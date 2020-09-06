package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.CategoryDTO;
import eu.mnrdesign.matned.final_project.model.Category;
import eu.mnrdesign.matned.final_project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> findAll(){
        return repo.findAll();
    }

    public void add(CategoryDTO c){
        repo.save(Category.apply(c));
    }

    public CategoryDTO findById(Long id){
        return CategoryDTO.apply(Objects.requireNonNull(repo.findById(id).orElse(null)));
    }
}
