package org.example.homework10.repository;

import org.example.homework10.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);

    List<Book> findByPriceGreaterThan(Double price);

    List<Book> findByPublishedYearBetween(Integer yearAfter, Integer yearBefore);

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByPagesLessThan(Integer pages);

    List<Book> findByTitleContaining(String phrase);
}
