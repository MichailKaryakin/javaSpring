package org.example.homework11.service.impl;

import org.example.homework11.dto.PersonDTO;
import org.example.homework11.entity.Person;
import org.example.homework11.repository.PersonRepository;
import org.example.homework11.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private PersonDTO toDTO(Person person) {
        return new PersonDTO(person.getId(), person.getTitle(), person.getYear());
    }

    private List<PersonDTO> toDTOList(List<Person> list) {
        return list.stream().map(this::toDTO).toList();
    }

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        Person saved = personRepository.save(new Person(null, personDTO.getTitle(), personDTO.getYear()));
        return toDTO(saved);
    }

    @Override
    public List<PersonDTO> createMultiple(List<PersonDTO> personDTOs) {
        List<Person> persons = personDTOs.stream()
                .map(dto -> new Person(null, dto.getTitle(), dto.getYear()))
                .toList();

        return toDTOList(personRepository.saveAll(persons));
    }

    @Override
    public List<PersonDTO> getAll() {
        return toDTOList(personRepository.findAll());
    }

    @Override
    public PersonDTO getById(Long id) {
        return personRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public PersonDTO getByTitle(String title) {
        Person person = personRepository.getByTitle(title);
        return person != null ? toDTO(person) : null;
    }

    @Override
    public List<PersonDTO> getAllSortedByTitle(boolean ascending) {
        return ascending
                ? toDTOList(personRepository.findAllByOrderByTitleAsc())
                : toDTOList(personRepository.findAllByOrderByTitleDesc());
    }

    @Override
    public List<PersonDTO> getAllSortedByYear(boolean ascending) {
        return ascending
                ? toDTOList(personRepository.findAllByOrderByYearAsc())
                : toDTOList(personRepository.findAllByOrderByYearDesc());
    }

    @Override
    public List<PersonDTO> getAllWithPaginationAndSorting(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return toDTOList(personRepository.findAll(pageable).getContent());
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        return personRepository.findById(id).map(p -> {
            p.setTitle(personDTO.getTitle());
            p.setYear(personDTO.getYear());
            return toDTO(personRepository.save(p));
        }).orElse(null);
    }

    @Override
    public PersonDTO updatePersonTitle(Long id, String title) {
        return personRepository.findById(id).map(p -> {
            p.setTitle(title);
            return toDTO(personRepository.save(p));
        }).orElse(null);
    }

    @Override
    public PersonDTO updatePersonYear(Long id, Integer year) {
        return personRepository.findById(id).map(p -> {
            p.setYear(year);
            return toDTO(personRepository.save(p));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }

    @Override
    public void deleteAllByTitle(String title) {
        personRepository.deleteAllByTitle(title);
    }

    @Override
    public void deleteAllByYearRange(Integer start, Integer end) {
        personRepository.deleteAllByYearBetween(start, end);
    }

    @Override
    public void deleteAllOlderThan(Integer year) {
        personRepository.deleteAllByYearLessThan(year);
    }

    @Override
    public void deleteAllYoungerThan(Integer year) {
        personRepository.deleteAllByYearGreaterThan(year);
    }

    @Override
    public List<PersonDTO> findAllByYearRange(Integer start, Integer end) {
        return toDTOList(personRepository.findAllByYearBetween(start, end));
    }

    @Override
    public List<PersonDTO> findAllByYoungerThan(Integer year) {
        return toDTOList(personRepository.findAllByYearGreaterThan(year));
    }

    @Override
    public List<PersonDTO> findAllByOlderThan(Integer year) {
        return toDTOList(personRepository.findAllByYearLessThan(year));
    }

    @Override
    public List<PersonDTO> findAllByTitleKey(String key) {
        return toDTOList(personRepository.findAllByTitleContainingIgnoreCase(key));
    }

    @Override
    public List<PersonDTO> findAllOlderThanAge(Integer age) {
        int currentYear = java.time.Year.now().getValue();
        int borderYear = currentYear - age;
        return toDTOList(personRepository.findAllByYearLessThan(borderYear));
    }

    @Override
    public List<PersonDTO> findAllByMinYearAndTitleKey(Integer year, String key) {
        return toDTOList(personRepository.findAllByYearGreaterThanEqualAndTitleContainingIgnoreCase(year, key));
    }

    @Override
    public Long getTotalCount() {
        return personRepository.count();
    }

    @Override
    public Long countByYearRange(Integer start, Integer end) {
        return personRepository.countByYearBetween(start, end);
    }

    @Override
    public boolean existsById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public boolean existsByTitle(String title) {
        return personRepository.existsByTitle(title);
    }
}
