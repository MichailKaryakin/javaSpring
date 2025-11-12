package org.example.homework7.controller;

import org.example.homework7.model.*;
import org.example.homework7.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с Rest Countries
 */
@RestController
@RequestMapping("/api/countries")
public class CountryController {
    /**
     * Сервис, которому контроллер передаёт параметры запроса
     */
    private final CountryService countryService;

    /**
     * Взятие бина CountryService из спринг контейнера
     */
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * GET запрос на поиск страны по имени
     */
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCountry(@PathVariable String name) {
        return countryService.searchCountry(name);
    }

    /**
     * GET запрос на поиск стран по региону
     */
    @GetMapping("/region/{region}")
    public ResponseEntity<?> getRegionCountries(@PathVariable String region) {
        return countryService.getRegionCountries(region);
    }

    /**
     * GET запрос на поиск стран по населению
     */
    @GetMapping("/population")
    public ResponseEntity<?> getByPopulation(@RequestParam int min, @RequestParam int max) {
        return countryService.getByPopulation(min, max);
    }

    /**
     * GET запрос на получение данных о кэше
     */
    @GetMapping("/cache/stats")
    public ResponseEntity<?> getCacheStats() {
        return countryService.getCacheStats();
    }

    /**
     * POST запрос на очистку кэша
     */
    @PostMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        return countryService.clearCache();
    }
}
