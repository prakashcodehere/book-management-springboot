package com.example.book_management.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class BookResDTO {

    private Long id;
    private String title;
    private String isbn;
    private BigDecimal price;

    private Set<String> authors;
    private Set<String> categories;
}
