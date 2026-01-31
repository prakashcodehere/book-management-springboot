package com.example.book_management.controller;

import com.example.book_management.dto.AuthorReqDTO;
import com.example.book_management.dto.AuthorResDTO;
import com.example.book_management.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/authors")
@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("createAuthor")
    public ResponseEntity<AuthorResDTO> createAuthor(@RequestBody AuthorReqDTO author) {
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @GetMapping("getAuthor/{id}")
    public ResponseEntity<AuthorResDTO> getAuthor(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("getAllAuthors")
    public ResponseEntity<Page<AuthorResDTO>> getAllAuthors(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(authorService.getAllAuthors(page,size));
    }
}

