package org.example.homework10.controller;

import org.example.homework10.entity.Author;
import org.example.homework10.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public Author add(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @GetMapping("/email")
    public List<Author> findByEmail(@RequestParam String email) {
        return authorService.findByEmail(email);
    }

    @GetMapping("/age/greater")
    public List<Author> findByAgeGreaterThan(@RequestParam Integer age) {
        return authorService.findByAgeGreaterThan(age);
    }

    @GetMapping("/age/between")
    public List<Author> findByAgeBetween(@RequestParam Integer min,
                                         @RequestParam Integer max) {
        return authorService.findByAgeBetween(min, max);
    }

    @GetMapping("/country")
    public List<Author> findByCountry(@RequestParam String country) {
        return authorService.findByCountry(country);
    }

    @GetMapping("/name")
    public List<Author> findByFirstNameOrLastName(@RequestParam String value) {
        return authorService.findByFirstNameOrLastName(value, value);
    }

    @GetMapping("/country/sort")
    public List<Author> findByCountryOrderByAgeDesc(@RequestParam String country) {
        return authorService.findByCountryOrderByAgeDesc(country);
    }

    @GetMapping("/top3-oldest")
    public List<Author> findTop3ByOrderByAgeDesc() {
        return authorService.findTop3ByOrderByAgeDesc();
    }
}
