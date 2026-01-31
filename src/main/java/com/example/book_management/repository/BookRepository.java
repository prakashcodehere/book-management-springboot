package com.example.book_management.repository;

import com.example.book_management.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> searchBookByTitle(String title, Pageable pageable);

    Page<Book> findByAuthors_Id(Long authorId, Pageable pageable);

    Page<Book> findByCategories_Name(String category, Pageable pageable);

}
