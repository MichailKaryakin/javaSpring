package org.example.homework12.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.homework12.dto.BookRequest;
import org.example.homework12.dto.BookResponse;
import org.example.homework12.entity.Book;
import org.example.homework12.exception.BookNotFoundException;
import org.example.homework12.exception.InvalidBookDataException;
import org.example.homework12.repository.BookRepository;
import org.example.homework12.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private void validateBook(BookRequest bookRequest) {

        if (bookRequest == null) {
            throw new InvalidBookDataException("Book request cannot be null");
        }

        if (bookRequest.title() == null || bookRequest.title().isBlank()) {
            throw new InvalidBookDataException("Title cannot be empty");
        }
        if (bookRequest.title().trim().length() < 2) {
            throw new InvalidBookDataException("Title must be at least 2 characters long");
        }

        if (bookRequest.author() == null || bookRequest.author().isBlank()) {
            throw new InvalidBookDataException("Author cannot be empty");
        }

        if (bookRequest.year() == null) {
            throw new InvalidBookDataException("Year cannot be null");
        }

        int currentYear = java.time.Year.now().getValue();
        if (bookRequest.year() < -2600 || bookRequest.year() > currentYear) {
            throw new InvalidBookDataException(
                    "Year must be between 2600 BC and " + currentYear + " AD"
            );
        }

        if (bookRequest.description() != null && bookRequest.description().trim().length() < 10) {
            throw new InvalidBookDataException("Description must be at least 10 characters long");
        }
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
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> result = bookRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<BookResponse> getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return ResponseEntity.ok(toResponse(book));
    }

    @Override
    public ResponseEntity<BookResponse> createBook(BookRequest request) {

        validateBook(request);

        Book book = Book.builder()
                .title(request.title())
                .author(request.author())
                .year(request.year())
                .description(request.description())
                .build();

        BookResponse bookResponse = toResponse(bookRepository.save(book));

        URI uri = URI.create("/api/books/book-by-id?id=" + bookResponse.id());
        return ResponseEntity.created(uri).body(bookResponse);
    }

    @Override
    public ResponseEntity<BookResponse> updateBook(BookRequest request, Long id) {

        validateBook(request);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setYear(request.year());
        book.setDescription(request.description());

        return ResponseEntity.ok(toResponse(bookRepository.save(book)));
    }
}
