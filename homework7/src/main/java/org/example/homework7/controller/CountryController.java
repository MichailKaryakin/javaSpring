package org.example.homework7.controller;

import org.example.homework7.model.*;
import org.example.homework7.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCountry(@PathVariable String name) {
        return countryService.searchCountry(name);
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<?> getRegionCountries(@PathVariable String region) {
        return countryService.getRegionCountries(region);
    }

    @GetMapping("/population")
    public ResponseEntity<?> getByPopulation(@RequestParam int min, @RequestParam int max) {
        return countryService.getByPopulation(min, max);
    }

    @GetMapping("/cache/stats")
    public ResponseEntity<?> getCacheStats() {
        return countryService.getCacheStats();
    }

    @PostMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        return countryService.clearCache();
    }
}
