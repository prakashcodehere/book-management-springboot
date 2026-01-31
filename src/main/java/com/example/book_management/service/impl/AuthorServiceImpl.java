package com.example.book_management.service.impl;


import com.example.book_management.dto.AuthorReqDTO;
import com.example.book_management.dto.AuthorResDTO;
import com.example.book_management.entity.Author;
import com.example.book_management.exception.ResourceNotFoundException;
import com.example.book_management.repository.AuthorRepository;
import com.example.book_management.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AuthorResDTO createAuthor(AuthorReqDTO authorReqDTO) {
        Author author = new Author();
        author.setName(authorReqDTO.getName());
        Author savedAuthor = authorRepository.save(author);

        return mapToResponse(savedAuthor);
    }

    @Override
    public Page<AuthorResDTO> getAllAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(i->mapToResponse(i));
    }

    @Override
    public AuthorResDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with this id:" + id));
        return mapToResponse(author);
    }

    private AuthorResDTO mapToResponse(Author author) {
        AuthorResDTO authorResDTO = new AuthorResDTO();
        authorResDTO.setId(author.getId());
        authorResDTO.setName(author.getName());

        return authorResDTO;
    }
}
