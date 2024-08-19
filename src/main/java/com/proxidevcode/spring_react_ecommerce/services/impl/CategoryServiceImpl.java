package com.proxidevcode.spring_react_ecommerce.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.CategoryMapper;
import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;
import com.proxidevcode.spring_react_ecommerce.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
                
    @Override
    public List<CategoryResponse> getAllcategories() {
         List<Category> categories = categoryRepository.findAll();  
        return categories.stream().map(mapper::mapToDto).toList();
    }

    @Override
    public CategoryResponse getCategoryDetail(Long id) {
        return mapper.mapToDto(categoryRepository.findById(id).get());
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest dto) {
        Category category = mapper.mapToEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        return mapper.mapToDto(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest dto) {
        Category existingCategory = categoryRepository.findById(id).get();
        existingCategory.setName(dto.name());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return mapper.mapToDto(updatedCategory);
    }

    @Override
    public Void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return null;
    }
    
}
