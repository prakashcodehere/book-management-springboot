package com.example.book_management.controller;

import com.example.book_management.dto.ApiResponseDTO;
import com.example.book_management.dto.BookReqDTO;
import com.example.book_management.dto.BookResDTO;
import com.example.book_management.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Create a new book", description = "Only ADMIN can create books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<ApiResponseDTO<BookResDTO>> createBook(
            @Valid @RequestBody BookReqDTO bookReqDTO) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Book created successfully",
                        bookService.createBook(bookReqDTO),
                        LocalDateTime.now()
                )
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("getBook/{id}")
    public ResponseEntity<ApiResponseDTO<BookResDTO>> getBookById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Book fetched successfully",
                        bookService.getBookById(id),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("getBooks")
    public ResponseEntity<ApiResponseDTO<Page<BookResDTO>>> getBooks(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Books fetched successfully",
                        bookService.getBooks(page, size, sort),
                        LocalDateTime.now()
                )
        );
    }

    @PutMapping("updateBook/{id}")
    public ResponseEntity<ApiResponseDTO<BookResDTO>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookReqDTO bookReqDTO) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Book updated successfully",
                        bookService.updateBook(id, bookReqDTO),
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("deleteBook/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Book deleted successfully",
                        null,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("searchByTitle")
    public ResponseEntity<ApiResponseDTO<Page<BookResDTO>>> searchBookByTitle(
            @RequestParam String title,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Books fetched by title",
                        bookService.searchBookByTitle(title, page, size),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("searchByAuthor")
    public ResponseEntity<ApiResponseDTO<Page<BookResDTO>>> searchBookByAuthor(
            @RequestParam Long id,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Books fetched by author",
                        bookService.getBookByAuthorId(id, page, size),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("searchByCategory")
    public ResponseEntity<ApiResponseDTO<Page<BookResDTO>>> searchBookByCategory(
            @RequestParam String category,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Books fetched by category",
                        bookService.getBookByCategory(category, page, size),
                        LocalDateTime.now()
                )
        );
    }
}
