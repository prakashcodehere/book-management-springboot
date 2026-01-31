package com.example.book_management.service;

import com.example.book_management.dto.BookReqDTO;
import com.example.book_management.dto.BookResDTO;
import org.springframework.data.domain.Page;


public interface BookService {

    BookResDTO createBook(BookReqDTO bookReqDTO);

    BookResDTO getBookById(Long id);

    Page<BookResDTO> getBooks(int page, int size, String sort);

    BookResDTO updateBook(Long id, BookReqDTO book);

    void deleteBook(Long id);

    Page<BookResDTO> searchBookByTitle(String title, int page, int size);

    Page<BookResDTO> getBookByAuthorId(Long authorId, int page, int size);

    Page<BookResDTO> getBookByCategory(String category, int page, int size);
}
