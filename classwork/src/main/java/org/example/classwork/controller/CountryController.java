package org.example.classwork.controller;

import org.example.classwork.mapper.CountryMapper;
import org.example.classwork.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final SimpleCache cache = new SimpleCache(1800000); // 30 минут TTL

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCountry(@PathVariable String name) {
        String key = "country_name_" + name.toUpperCase();
        CountryListResponse cached = cache.get(key);

        if (cached != null) {
            System.out.println("Данные по '" + name + "' взяты из кэша");
            return ResponseEntity.ok(cached);
        }

        System.out.println("Запрашиваются данные из API для страны: " + name);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://restcountries.com/v3.1/name/" + name;
        ResponseEntity<CountryApiResponse[]> response =
                restTemplate.getForEntity(url, CountryApiResponse[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<CountryResponse> countries =
                    CountryMapper.toCountryListResponse(response.getBody());

            CountryListResponse listResponse = new CountryListResponse();
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

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://restcountries.com/v3.1/region/" + region;
        ResponseEntity<CountryApiResponse[]> response =
                restTemplate.getForEntity(url, CountryApiResponse[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<CountryResponse> countries =
                    CountryMapper.toCountryListResponse(response.getBody());

            CountryListResponse listResponse = new CountryListResponse();
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

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://restcountries.com/v3.1/all";
            ResponseEntity<CountryApiResponse[]> response =
                    restTemplate.getForEntity(url, CountryApiResponse[].class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                System.out.println("Ошибка при получении данных из API");
                return ResponseEntity.status(response.getStatusCode()).body("Ошибка при получении данных из API");
            }

            countries = CountryMapper.toCountryListResponse(response.getBody());

            CountryListResponse listResponse = new CountryListResponse();
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
