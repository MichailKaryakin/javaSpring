package org.example.homework11.service;

import org.example.homework11.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    // CREATE
    PersonDTO create(PersonDTO personDTO);

    List<PersonDTO> createMultiple(List<PersonDTO> personDTO);

    // READ
    List<PersonDTO> getAll();

    PersonDTO getById(Long id);

    PersonDTO getByTitle(String title);

    List<PersonDTO> getAllSortedByTitle(boolean ascending);

    List<PersonDTO> getAllSortedByYear(boolean ascending);

    List<PersonDTO> getAllWithPaginationAndSorting(int page, int size, String sortBy);

    // UPDATE
    PersonDTO updatePerson(Long id, PersonDTO personDTO);

    PersonDTO updatePersonTitle(Long id, String title);

    PersonDTO updatePersonYear(Long id, Integer year);

    // DELETE
    void delete(Long id);

    void deleteAll();

    void deleteAllByTitle(String title);

    void deleteAllByYearRange(Integer start, Integer end);

    void deleteAllOlderThan(Integer year);

    void deleteAllYoungerThan(Integer year);

    // FILTER
    List<PersonDTO> findAllByYearRange(Integer start, Integer end);

    List<PersonDTO> findAllByYoungerThan(Integer year);

    List<PersonDTO> findAllByOlderThan(Integer year);

    List<PersonDTO> findAllByTitleKey(String key);

    List<PersonDTO> findAllOlderThanAge(Integer age);

    List<PersonDTO> findAllByMinYearAndTitleKey(Integer year, String key);

    // statistic
    Long getTotalCount();

    Long countByYearRange(Integer start, Integer end);

    boolean existsById(Long id);

    boolean existsByTitle(String title);
}
