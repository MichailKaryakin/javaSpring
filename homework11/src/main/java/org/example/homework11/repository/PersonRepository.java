package org.example.homework11.repository;

import org.example.homework11.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person getByTitle(String title);

    List<Person> findAllByOrderByTitleAsc();

    List<Person> findAllByOrderByTitleDesc();

    List<Person> findAllByOrderByYearAsc();

    List<Person> findAllByOrderByYearDesc();

    void deleteAllByTitle(String title);

    void deleteAllByYearBetween(Integer start, Integer end);

    void deleteAllByYearLessThan(Integer year);

    void deleteAllByYearGreaterThan(Integer year);

    List<Person> findAllByYearBetween(Integer start, Integer end);

    List<Person> findAllByYearGreaterThan(Integer year);

    List<Person> findAllByYearLessThan(Integer year);

    List<Person> findAllByTitleContainingIgnoreCase(String key);

    List<Person> findAllByYearGreaterThanEqualAndTitleContainingIgnoreCase(Integer year, String key);

    Long countByYearBetween(Integer start, Integer end);

    boolean existsByTitle(String title);
}
