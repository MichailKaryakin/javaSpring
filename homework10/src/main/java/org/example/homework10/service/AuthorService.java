package org.example.homework10.service;

import org.example.homework10.entity.Author;
import org.example.homework10.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

    public List<Author> findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public List<Author> findByAgeGreaterThan(Integer age) {
        return authorRepository.findByAgeGreaterThan(age);
    }

    public List<Author> findByAgeBetween(Integer ageStart, Integer ageEnd) {
        return authorRepository.findByAgeBetween(ageStart, ageEnd);
    }

    public List<Author> findByCountry(String country) {
        return authorRepository.findByCountry(country);
    }

    public List<Author> findByFirstNameOrLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    public List<Author> findByCountryOrderByAgeDesc(String country) {
        return authorRepository.findByCountryOrderByAgeDesc(country);
    }

    public List<Author> findTop3ByOrderByAgeDesc() {
        return authorRepository.findTop3ByOrderByAgeDesc();
    }
}
