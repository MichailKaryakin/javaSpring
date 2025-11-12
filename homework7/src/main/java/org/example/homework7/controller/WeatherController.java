package org.example.homework7.controller;

import org.example.homework7.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с Weather API
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    /**
     * Сервис, которому контроллер передаёт параметры запроса
     */
    private final WeatherService weatherService;

    /**
     * Взятие бина WeatherService из спринг контейнера
     */
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * GET запрос на получение данных о погоде по городу
     */
    @GetMapping("")
    public ResponseEntity<?> getWeatherData(@RequestParam String city) {
        return weatherService.getWeatherData(city);
    }

    /**
     * GET запрос на получение данных о кэше
     */
    @GetMapping("/cache/stats")
    public ResponseEntity<?> getCacheStats() {
        return weatherService.getCacheStats();
    }

    /**
     * POST запрос на очистку кэша
     */
    @PostMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        return weatherService.clearCache();
    }
}
