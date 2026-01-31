package com.example.book_management.controller;

import com.example.book_management.dto.CategoryReqDTO;
import com.example.book_management.dto.CategoryResDTO;
import com.example.book_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("createCategory")
    public ResponseEntity<CategoryResDTO> createCategory(@RequestBody CategoryReqDTO categoryReqDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryReqDTO));
    }

    @GetMapping("getCategory/{id}")
    public ResponseEntity<CategoryResDTO> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("getAllCategories")
    public ResponseEntity<Page<CategoryResDTO>> getAllCategories(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(categoryService.getAllCategories(page,size));
    }
}
