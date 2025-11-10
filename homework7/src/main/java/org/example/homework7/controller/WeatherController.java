package org.example.homework7.controller;

import org.example.homework7.mapper.WeatherMapper;
import org.example.homework7.model.SimpleCache;
import org.example.homework7.model.WeatherApiResponse;
import org.example.homework7.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final SimpleCache cache;
    @Value("${weather.api.key:}")
    private String apiKey;
    @Value("${weather.cache.ttl:}")
    private long ttl;
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherController(SimpleCache cache, RestTemplate restTemplate) {
        this.cache = cache;
        this.restTemplate = restTemplate;
        cache.setTimeToLive(ttl);
    }

    @GetMapping("")
    public ResponseEntity<?> getWeatherData(@RequestParam String city) {
        String key = city.toUpperCase();
        WeatherResponse cached = cache.get(key);

        if (cached != null) {
            System.out.println("Данные по городу: " + city + " - взяты из кэша");
            return ResponseEntity.ok(cached);
        }

        String requestUrl
                = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(requestUrl, WeatherApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            WeatherResponse weatherResponse = WeatherMapper.toWeatherResponse(response.getBody());

            cache.put(key, weatherResponse);
            System.out.println("Данные по городу: " + city + " - сохранены в кэш");

            return ResponseEntity.ok(weatherResponse);
        }

        System.out.println("Ошибка при получении данных для: " + city);
        return ResponseEntity.status(response.getStatusCode()).body("Ошибка при получении данных");
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
