package org.example.homework10.controller;

import org.example.homework10.entity.Book;
import org.example.homework10.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public Book add(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/title")
    public List<Book> findByTitle(@RequestParam String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping("/price/greater")
    public List<Book> findByPriceGreaterThan(@RequestParam Double price) {
        return bookService.findByPriceGreaterThan(price);
    }

    @GetMapping("/year/between")
    public List<Book> findByPublishedYearBetween(@RequestParam Integer from,
                                                 @RequestParam Integer to) {
        return bookService.findByPublishedYearBetween(from, to);
    }

    @GetMapping("/author")
    public List<Book> findByAuthor(@RequestParam Long authorId) {
        return bookService.findByAuthorId(authorId);
    }

    @GetMapping("/pages/less")
    public List<Book> findByPagesLessThan(@RequestParam Integer pages) {
        return bookService.findByPagesLessThan(pages);
    }

    @GetMapping("/title/contains")
    public List<Book> findByTitleContaining(@RequestParam String word) {
        return bookService.findByTitleContaining(word);
    }
}
