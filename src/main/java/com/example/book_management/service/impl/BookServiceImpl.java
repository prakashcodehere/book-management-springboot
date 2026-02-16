package com.example.book_management.service.impl;

import com.example.book_management.dto.BookReqDTO;
import com.example.book_management.dto.BookResDTO;
import com.example.book_management.dto.PageResponseDTO;
import com.example.book_management.entity.Author;
import com.example.book_management.entity.Book;
import com.example.book_management.entity.Category;
import com.example.book_management.exception.ResourceNotFoundException;
import com.example.book_management.repository.AuthorRepository;
import com.example.book_management.repository.BookRepository;
import com.example.book_management.repository.CategoryRepository;
import com.example.book_management.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    @CacheEvict(value = "books", allEntries = true)
    public BookResDTO createBook(BookReqDTO bookReqDTO) {
        Book book = reqToBook(bookReqDTO);
        Book savedBook = bookRepository.save(book);

        return mapToResponse(savedBook);
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public BookResDTO getBookById(Long id) {
        Book bookFound = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with this id: " + id));
        return mapToResponse(bookFound);
    }

    @Override
    @Cacheable(value = "booksPage", key = "'page_'+#page+'_size_'+#size+'_sort_'+#sort")
    public PageResponseDTO<BookResDTO> getBooks(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findByTitleContainingIgnoreCase(sort, pageable);
        return new PageResponseDTO<>(
                bookPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList(),
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }

    @Override
    @CacheEvict(value = "books", allEntries = true)
    public BookResDTO updateBook(Long id, BookReqDTO bookReqDTO) {
        Book book = reqToBook(bookReqDTO);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return mapToResponse(updatedBook);
    }

    @Override
    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResDTO> searchBookByTitle(String title,int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> books = bookRepository.searchBookByTitle(title,pageable);
        return books.map(i->mapToResponse(i));
    }

    @Override
    public Page<BookResDTO> getBookByAuthorId(Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> books = bookRepository.findByAuthors_Id(authorId,pageable);
        return books.map(i->mapToResponse(i));
    }

    @Override
    public Page<BookResDTO> getBookByCategory(String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> books = bookRepository.findByCategories_Name(categoryName,pageable);
        return books.map(i->mapToResponse(i));
    }

    private BookResDTO mapToResponse(Book book) {
        BookResDTO dto = new BookResDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());

        dto.setAuthors(
                book.getAuthors().stream()
                        .map(Author::getName)
                        .collect(Collectors.toSet())
        );

        dto.setCategories(
                book.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    private Book reqToBook(BookReqDTO bookReqDTO) {
        Book book = new Book();
        book.setTitle(bookReqDTO.getTitle());
        book.setIsbn(bookReqDTO.getIsbn());
        book.setPrice(bookReqDTO.getPrice());

        Set<Author> authors = authorRepository.findAllById(bookReqDTO.getAuthorIds()).stream().collect(Collectors.toSet());

        for (Author author : authors) {
            author.getBooks().add(book);
        }
        book.setAuthors(authors);

        Set<Category> categories = categoryRepository.findAllById(bookReqDTO.getCategoryIds()).stream().collect(Collectors.toSet());

        for (Category category : categories) {
            category.getBooks().add(book);
        }
        book.setCategories(categories);

        return book;
    }

}
