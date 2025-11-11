package org.example.homework7.services;

import jakarta.annotation.PostConstruct;
import org.example.homework7.mapper.CountryMapper;
import org.example.homework7.model.CountryApiResponse;
import org.example.homework7.model.CountryListResponse;
import org.example.homework7.model.CountryResponse;
import org.example.homework7.model.SimpleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CountryService {
    private final SimpleCache cache;
    private CountryListResponse cachedResponse;
    @Value("${country.cache.ttl:}")
    private long ttl;
    private final RestTemplate restTemplate;
    private final ApplicationContext context;

    @Autowired
    public CountryService(SimpleCache cache, RestTemplate restTemplate, ApplicationContext context) {
        this.cache = cache;
        this.restTemplate = restTemplate;
        this.context = context;
    }

    @PostConstruct
    public void setTtl() {
        cache.setTimeToLive(ttl);
    }

    public ResponseEntity<?> searchCountry(String name) {
        String key = "country_name_" + name.toUpperCase();
        cachedResponse = cache.get(key);

        if (cachedResponse != null) {
            System.out.println("Данные по '" + name + "' взяты из кэша");
            return ResponseEntity.ok(cachedResponse);
        }

        System.out.println("Запрашиваются данные из API для страны: " + name);

        String url = "https://restcountries.com/v3.1/name/" + name;
        ResponseEntity<CountryApiResponse[]> response =
                restTemplate.getForEntity(url, CountryApiResponse[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<CountryResponse> countries =
                    CountryMapper.toCountryListResponse(response.getBody());

            CountryListResponse listResponse = context.getBean(CountryListResponse.class);
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

    public ResponseEntity<?> getRegionCountries(String region) {
        String key = "country_region_" + region.toUpperCase();
        cachedResponse = cache.get(key);

        if (cachedResponse != null) {
            System.out.println("Данные по региону '" + region + "' взяты из кэша");
            return ResponseEntity.ok(cachedResponse);
        }

        System.out.println("Запрашиваются данные из API для региона: " + region);

        String url = "https://restcountries.com/v3.1/region/" + region;
        ResponseEntity<CountryApiResponse[]> response =
                restTemplate.getForEntity(url, CountryApiResponse[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<CountryResponse> countries =
                    CountryMapper.toCountryListResponse(response.getBody());

            CountryListResponse listResponse = context.getBean(CountryListResponse.class);
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

    public ResponseEntity<?> getByPopulation(int min, int max) {
        String key = "country_all";
        cachedResponse = cache.get(key);

        List<CountryResponse> countries;

        if (cachedResponse != null) {
            System.out.println("Данные по всем странам взяты из кэша");
            countries = cachedResponse.getCountries();
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

            CountryListResponse listResponse = context.getBean(CountryListResponse.class);
            listResponse.setCountries(countries);
            listResponse.setTotalResults(countries.size());
            listResponse.setFilterApplied("all");

            cache.put(key, listResponse);
            System.out.println("Данные по всем странам сохранены в кэш");
        }

        List<CountryResponse> filtered = countries.stream()
                .filter(c -> c.getPopulation() >= min && c.getPopulation() <= max)
                .toList();

        CountryListResponse listResponse = context.getBean(CountryListResponse.class);
        listResponse.setCountries(filtered);
        listResponse.setTotalResults(filtered.size());
        listResponse.setFilterApplied("population=" + min + "-" + max);

        System.out.println("Отфильтровано стран по населению: " + filtered.size());
        return ResponseEntity.ok(listResponse);
    }

    public ResponseEntity<?> getCacheStats() {
        return ResponseEntity.ok(cache.getStats());
    }

    public ResponseEntity<?> clearCache() {
        cache.clear();
        return ResponseEntity.ok("Кэш очищен");
    }
}
