package org.example.homework12.controller;

import lombok.RequiredArgsConstructor;
import org.example.homework12.dto.BookResponse;
import org.example.homework12.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    public BookResponse getAllBooks() {
        return null;
    }
}
