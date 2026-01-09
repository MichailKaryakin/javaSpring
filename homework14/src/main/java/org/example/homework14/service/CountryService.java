package org.example.homework14.service;

import org.example.homework14.entity.Country;
import org.example.homework14.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public Page<Country> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Country save(Country country) {
        country.getCities().forEach(c -> c.setCountry(country));
        return repository.save(country);
    }

    public Country getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
