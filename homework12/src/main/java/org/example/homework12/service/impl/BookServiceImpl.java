package org.example.homework12.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.homework12.dto.BookRequest;
import org.example.homework12.dto.BookResponse;
import org.example.homework12.entity.Book;
import org.example.homework12.repository.BookRepository;
import org.example.homework12.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    private boolean validateBook(BookRequest bookRequest) {
        return true;
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getDescription()
        );
    }

    @Override
    public ResponseEntity<@NonNull List<BookResponse>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> bookResponses = new ArrayList<>();
        books.forEach(book -> bookResponses.add(toResponse(book)));
        return ResponseEntity.ok(bookResponses);
    }

    @Override
    public ResponseEntity<@NonNull BookResponse> getBookById(Long id) {
        Optional<@NonNull Book> book = bookRepository.findById(id);
        return book.map(value -> ResponseEntity.ok(toResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<@NonNull BookResponse> createBook(BookRequest request) {
        if (validateBook(request)) {
            Book book = Book.builder()
                    .title(request.title())
                    .author(request.author())
                    .year(request.year())
                    .description(request.description())
                    .build();

            return ResponseEntity.ok(toResponse(bookRepository.save(book)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<@NonNull BookResponse> updateBook(BookRequest request, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if (validateBook(request)) {
            book.setTitle(request.title());
            book.setDescription(request.description());
            book.setYear(request.year());
            book.setAuthor(request.author());

            return ResponseEntity.ok(toResponse(bookRepository.save(book)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
