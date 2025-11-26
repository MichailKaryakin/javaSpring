package org.example.homework10.service;

import org.example.homework10.entity.Book;
import org.example.homework10.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findByPriceGreaterThan(Double price) {
        return bookRepository.findByPriceGreaterThan(price);
    }

    public List<Book> findByPublishedYearBetween(Integer yearAfter, Integer yearBefore) {
        return bookRepository.findByPublishedYearBetween(yearAfter, yearBefore);
    }

    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<Book> findByPagesLessThan(Integer pages) {
        return bookRepository.findByPagesLessThan(pages);
    }

    public List<Book> findByTitleContaining(String phrase) {
        return bookRepository.findByTitleContaining(phrase);
    }
}
