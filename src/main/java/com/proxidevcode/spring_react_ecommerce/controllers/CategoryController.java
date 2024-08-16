package com.proxidevcode.spring_react_ecommerce.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAllcategories () {
        return categoryRepository.findAll();  
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping(value = "/{id}")
    public Category updateCategory(@RequestBody Category category, @PathVariable Long id){
        Category existingCategory = categoryRepository.findById(id).get();
        existingCategory.setName(category.getName());
        Category savedCategory = categoryRepository.save(existingCategory);
        return savedCategory;
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
    return categoryRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
    categoryRepository.deleteById(id);
    }


    // @GetMapping("/{name}")
    // public String getCategory(@PathVariable String name) {
    //     return "Category " + name;
    // }
}
