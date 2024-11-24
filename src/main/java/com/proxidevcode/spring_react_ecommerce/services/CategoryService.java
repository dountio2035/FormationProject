package com.proxidevcode.spring_react_ecommerce.services;

import java.util.List;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;

public interface CategoryService {
    List<CategoryResponse> getAllcategories();

    CategoryResponse getCategoryDetail(Long id);

    CategoryResponse createCategory(CategoryRequest dto);

    CategoryResponse updateCategory(Long id, CategoryRequest dto);

    void deleteCategory(Long id);
}
