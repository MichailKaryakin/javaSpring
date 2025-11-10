package org.example.homework7.services;

import org.example.homework7.mapper.WeatherMapper;
import org.example.homework7.model.SimpleCache;
import org.example.homework7.model.WeatherApiResponse;
import org.example.homework7.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final SimpleCache cache;
    @Value("${weather.api.key:}")
    private String apiKey;
    @Value("${weather.cache.ttl:}")
    private long ttl;
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(SimpleCache cache, RestTemplate restTemplate) {
        this.cache = cache;
        this.restTemplate = restTemplate;
        cache.setTimeToLive(ttl);
    }

    public ResponseEntity<?> getWeatherData(String city) {
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

    public ResponseEntity<?> getCacheStats() {
        return ResponseEntity.ok(cache.getStats());
    }

    public ResponseEntity<?> clearCache() {
        cache.clear();
        return ResponseEntity.ok("Кэш очищен");
    }
}
