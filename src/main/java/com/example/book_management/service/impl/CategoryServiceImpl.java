package com.example.book_management.service.impl;

import com.example.book_management.dto.CategoryReqDTO;
import com.example.book_management.dto.CategoryResDTO;
import com.example.book_management.entity.Category;
import com.example.book_management.exception.ResourceNotFoundException;
import com.example.book_management.repository.CategoryRepository;
import com.example.book_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryService;

    @Override
    public CategoryResDTO createCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category();
        category.setName(categoryReqDTO.getName());
        Category savedCategory = categoryService.save(category);

        return mapToResponse(savedCategory);
    }

    @Override
    public Page<CategoryResDTO> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryService.findAll(pageable);
        return categories.map(i->mapToResponse(i));
    }

    @Override
    public CategoryResDTO getCategoryById(Long id) {
        Category category = categoryService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with this id:" + id));
        return mapToResponse(category);
    }

    private CategoryResDTO mapToResponse(Category category) {
        CategoryResDTO categoryResDTO = new CategoryResDTO();
        categoryResDTO.setId(category.getId());
        categoryResDTO.setName(category.getName());

        return categoryResDTO;
    }

}
