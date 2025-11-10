package org.example.homework7.controller;

import org.example.homework7.mapper.CountryMapper;
import org.example.homework7.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final SimpleCache cache;
    private final CountryListResponse listResponse;
    @Value("${country.cache.ttl:}")
    private long ttl;
    private final RestTemplate restTemplate;

    @Autowired
    public CountryController(SimpleCache cache, RestTemplate restTemplate, CountryListResponse countryListResponse) {
        this.cache = cache;
        this.listResponse = countryListResponse;
        this.restTemplate = restTemplate;
        cache.setTimeToLive(ttl);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCountry(@PathVariable String name) {
        String key = "country_name_" + name.toUpperCase();
        CountryListResponse cached = cache.get(key);

        if (cached != null) {
            System.out.println("Данные по '" + name + "' взяты из кэша");
            return ResponseEntity.ok(cached);
        }

        System.out.println("Запрашиваются данные из API для страны: " + name);

        String url = "https://restcountries.com/v3.1/name/" + name;
        ResponseEntity<CountryApiResponse[]> response =
                restTemplate.getForEntity(url, CountryApiResponse[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<CountryResponse> countries =
                    CountryMapper.toCountryListResponse(response.getBody());

            listResponse.setCountries(countries);
            listResponse.setTotalResults(countries.size());
            listResponse.setFilterApplied("name=" + name);

            cache.put(key, listResponse);
            System.out.println("Данные по '" + name + "' сохранены в кэш");
            return ResponseEntity.ok(listResponse);
        }

        System.out.println("Ошибка при получении данных для: " + name);
        return ResponseEntity.status(response.getStatusCode()).body("Ошибка при получении данных");
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<?> getRegionCountries(@PathVariable String region) {
        String key = "country_region_" + region.toUpperCase();
        CountryListResponse cached = cache.get(key);

        if (cached != null) {
            System.out.println("Данные по региону '" + region + "' взяты из кэша");
            return ResponseEntity.ok(cached);
        }

        System.out.println("Запрашиваются данные из API для региона: " + region);

        String url = "https://restcountries.com/v3.1/region/" + region;
        ResponseEntity<CountryApiResponse[]> response =
                restTemplate.getForEntity(url, CountryApiResponse[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<CountryResponse> countries =
                    CountryMapper.toCountryListResponse(response.getBody());

            listResponse.setCountries(countries);
            listResponse.setTotalResults(countries.size());
            listResponse.setFilterApplied("region=" + region);

            cache.put(key, listResponse);
            System.out.println("Данные по региону '" + region + "' сохранены в кэш");
            return ResponseEntity.ok(listResponse);
        }

        System.out.println("Ошибка при получении данных для региона: " + region);
        return ResponseEntity.status(response.getStatusCode()).body("Ошибка при получении данных");
    }

    @GetMapping("/population")
    public ResponseEntity<?> getByPopulation(@RequestParam int min, @RequestParam int max) {
        String key = "country_all";
        CountryListResponse cached = cache.get(key);

        List<CountryResponse> countries;

        if (cached != null) {
            System.out.println("Данные по всем странам взяты из кэша");
            countries = cached.getCountries();
        } else {
            System.out.println("Запрашиваются данные по всем странам из API");

            String url = "https://restcountries.com/v3.1/all";
            ResponseEntity<CountryApiResponse[]> response =
                    restTemplate.getForEntity(url, CountryApiResponse[].class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                System.out.println("Ошибка при получении данных из API");
                return ResponseEntity.status(response.getStatusCode()).body("Ошибка при получении данных из API");
            }

            countries = CountryMapper.toCountryListResponse(response.getBody());

            listResponse.setCountries(countries);
            listResponse.setTotalResults(countries.size());
            listResponse.setFilterApplied("all");

            cache.put(key, listResponse);
            System.out.println("Данные по всем странам сохранены в кэш");
        }

        List<CountryResponse> filtered = countries.stream()
                .filter(c -> c.getPopulation() >= min && c.getPopulation() <= max)
                .toList();

        CountryListResponse filteredResponse = new CountryListResponse();
        filteredResponse.setCountries(filtered);
        filteredResponse.setTotalResults(filtered.size());
        filteredResponse.setFilterApplied("population=" + min + "-" + max);

        System.out.println("Отфильтровано стран по населению: " + filtered.size());
        return ResponseEntity.ok(filteredResponse);
    }

    @GetMapping("/cache/stats")
    public ResponseEntity<?> getCacheStats() {
        return ResponseEntity.ok(cache.getStats());
    }

    @PostMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        cache.clear();
        return ResponseEntity.ok("Кэш очищен");
    }
}
