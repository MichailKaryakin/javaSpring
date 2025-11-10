package org.example.homework7.controller;

import org.example.homework7.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("")
    public ResponseEntity<?> getWeatherData(@RequestParam String city) {
        return weatherService.getWeatherData(city);
    }

    @GetMapping("/cache/stats")
    public ResponseEntity<?> getCacheStats() {
        return weatherService.getCacheStats();
    }

    @PostMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        return weatherService.clearCache();
    }
}
