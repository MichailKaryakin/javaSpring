package org.example.homework12.controller;

import lombok.RequiredArgsConstructor;
import org.example.homework12.dto.BookRequest;
import org.example.homework12.dto.BookResponse;
import org.example.homework12.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book-by-id")
    public ResponseEntity<BookResponse> getBookById(@RequestParam Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @PutMapping("/change")
    public ResponseEntity<BookResponse> changeBook(@RequestBody BookRequest bookRequest, @RequestParam Long id) {
        return bookService.updateBook(bookRequest, id);
    }
}
