package org.example.classwork.controller;

import org.example.classwork.mapper.WeatherMapper;
import org.example.classwork.model.SimpleCache;
import org.example.classwork.model.WeatherApiResponse;
import org.example.classwork.model.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final SimpleCache cache = new SimpleCache(300000);

    @GetMapping("")
    public ResponseEntity<?> getWeatherData(@RequestParam String city) {
        String key = city.toUpperCase();
        WeatherResponse cached = cache.get(key);

        if (cached != null) {
            System.out.println("Данные по городу: " + city + " - взяты из кэша");
            return ResponseEntity.ok(cached);
        }

        RestTemplate restTemplate = new RestTemplate();
        String apiKey = "5d05875a86d640886fd7411d9c47a972";
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
