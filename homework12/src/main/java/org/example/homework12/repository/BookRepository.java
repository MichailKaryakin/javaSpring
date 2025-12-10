package org.example.homework12.repository;

import lombok.NonNull;
import org.example.homework12.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<@NonNull Book, @NonNull Long> {

}
