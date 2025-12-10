package org.example.homework12.service;

import lombok.NonNull;
import org.example.homework12.dto.BookRequest;
import org.example.homework12.dto.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<@NonNull List<BookResponse>> getAllBooks();
    ResponseEntity<@NonNull BookResponse> getBookById(Long id);
    ResponseEntity<@NonNull BookResponse> createBook(BookRequest bookRequest);
    ResponseEntity<@NonNull BookResponse> updateBook(BookRequest bookRequest, Long id);
}
