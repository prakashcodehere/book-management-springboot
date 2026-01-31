package com.example.book_management.service;


import com.example.book_management.dto.AuthorReqDTO;
import com.example.book_management.dto.AuthorResDTO;
import org.springframework.data.domain.Page;

public interface AuthorService {

    AuthorResDTO createAuthor(AuthorReqDTO authorReqDTO);

    Page <AuthorResDTO> getAllAuthors(int page, int size);

    AuthorResDTO getAuthorById(Long id);
}
