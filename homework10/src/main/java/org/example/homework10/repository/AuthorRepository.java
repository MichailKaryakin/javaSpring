package org.example.homework10.repository;

import org.example.homework10.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByEmail(String email);

    List<Author> findByAgeGreaterThan(Integer age);

    List<Author> findByAgeBetween(Integer ageStart, Integer ageEnd);

    List<Author> findByCountry(String country);

    List<Author> findByFirstNameOrLastName(String firstName, String lastName);

    List<Author> findByCountryOrderByAgeDesc(String country);

    List<Author> findTop3ByOrderByAgeDesc();
}
