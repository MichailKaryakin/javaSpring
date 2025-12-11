package org.example.homework12.service;

import org.example.homework12.dto.BookRequest;
import org.example.homework12.dto.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<List<BookResponse>> getAllBooks();
    ResponseEntity<BookResponse> getBookById(Long id);
    ResponseEntity<BookResponse> createBook(BookRequest bookRequest);
    ResponseEntity<BookResponse> updateBook(BookRequest bookRequest, Long id);
}
