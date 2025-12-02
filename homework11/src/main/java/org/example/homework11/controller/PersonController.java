package org.example.homework11.controller;

import org.example.homework11.dto.PersonDTO;
import org.example.homework11.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // CREATE
    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO personDTO) {
        return personService.create(personDTO);
    }

    @PostMapping("/batch")
    public List<PersonDTO> createMultiple(@RequestBody List<PersonDTO> personDTOs) {
        return personService.createMultiple(personDTOs);
    }

    // READ
    @GetMapping
    public List<PersonDTO> getAll() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public PersonDTO getById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @GetMapping("/title/{title}")
    public PersonDTO getByTitle(@PathVariable String title) {
        return personService.getByTitle(title);
    }

    @GetMapping("/sorted/title")
    public List<PersonDTO> getAllSortedByTitle(@RequestParam(defaultValue = "true") boolean asc) {
        return personService.getAllSortedByTitle(asc);
    }

    @GetMapping("/sorted/year")
    public List<PersonDTO> getAllSortedByYear(@RequestParam(defaultValue = "true") boolean asc) {
        return personService.getAllSortedByYear(asc);
    }

    @GetMapping("/page")
    public List<PersonDTO> getPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "title") String sortBy
    ) {
        return personService.getAllWithPaginationAndSorting(page, size, sortBy);
    }

    // FILTERS
    @GetMapping("/year-range")
    public List<PersonDTO> findAllByYearRange(
            @RequestParam Integer start,
            @RequestParam Integer end
    ) {
        return personService.findAllByYearRange(start, end);
    }

    @GetMapping("/older-than")
    public List<PersonDTO> findAllOlderThan(@RequestParam Integer year) {
        return personService.findAllByOlderThan(year);
    }

    @GetMapping("/younger-than")
    public List<PersonDTO> findAllYoungerThan(@RequestParam Integer year) {
        return personService.findAllByYoungerThan(year);
    }

    @GetMapping("/title-key")
    public List<PersonDTO> findAllByTitleKey(@RequestParam String key) {
        return personService.findAllByTitleKey(key);
    }

    @GetMapping("/older-than-age")
    public List<PersonDTO> findAllOlderThanAge(@RequestParam Integer age) {
        return personService.findAllOlderThanAge(age);
    }

    @GetMapping("/min-year-title")
    public List<PersonDTO> findAllByMinYearAndTitleKey(
            @RequestParam Integer year,
            @RequestParam String key
    ) {
        return personService.findAllByMinYearAndTitleKey(year, key);
    }

    // UPDATE
    @PutMapping("/{id}")
    public PersonDTO updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(id, personDTO);
    }

    @PatchMapping("/{id}/title")
    public PersonDTO updateTitle(@PathVariable Long id, @RequestParam String title) {
        return personService.updatePersonTitle(id, title);
    }

    @PatchMapping("/{id}/year")
    public PersonDTO updateYear(@PathVariable Long id, @RequestParam Integer year) {
        return personService.updatePersonYear(id, year);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        personService.delete(id);
    }

    @DeleteMapping
    public void deleteAll() {
        personService.deleteAll();
    }

    @DeleteMapping("/title")
    public void deleteAllByTitle(@RequestParam String title) {
        personService.deleteAllByTitle(title);
    }

    @DeleteMapping("/year-range")
    public void deleteAllByYearRange(@RequestParam Integer start, @RequestParam Integer end) {
        personService.deleteAllByYearRange(start, end);
    }

    @DeleteMapping("/older-than")
    public void deleteAllOlderThan(@RequestParam Integer year) {
        personService.deleteAllOlderThan(year);
    }

    @DeleteMapping("/younger-than")
    public void deleteAllYoungerThan(@RequestParam Integer year) {
        personService.deleteAllYoungerThan(year);
    }

    // statistic
    @GetMapping("/count")
    public Long getTotalCount() {
        return personService.getTotalCount();
    }

    @GetMapping("/count/year-range")
    public Long countByYearRange(@RequestParam Integer start, @RequestParam Integer end) {
        return personService.countByYearRange(start, end);
    }

    @GetMapping("/exists/id/{id}")
    public boolean existsById(@PathVariable Long id) {
        return personService.existsById(id);
    }

    @GetMapping("/exists/title/{title}")
    public boolean existsByTitle(@PathVariable String title) {
        return personService.existsByTitle(title);
    }
}
