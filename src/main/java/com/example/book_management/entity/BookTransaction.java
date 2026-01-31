package com.example.book_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "book_transactions")
public class BookTransaction extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private LocalDate borrowDate;
    private LocalDate returnDate;
}

