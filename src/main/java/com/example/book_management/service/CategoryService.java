package com.example.book_management.service;

import com.example.book_management.dto.CategoryReqDTO;
import com.example.book_management.dto.CategoryResDTO;
import org.springframework.data.domain.Page;

public interface CategoryService {

    CategoryResDTO createCategory(CategoryReqDTO categoryReqDTO);

    Page<CategoryResDTO> getAllCategories(int page, int size);

    CategoryResDTO getCategoryById(Long id);
}
