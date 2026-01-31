package com.example.book_management.repository;

import com.example.book_management.entity.Book;
import com.example.book_management.entity.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookTransactionRepository extends JpaRepository<BookTransaction,Long> {

    List<BookTransaction> findByUserId(Long userId);

}
