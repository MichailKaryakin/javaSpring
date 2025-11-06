package org.example.homework6.controller;

import org.example.homework6.model.MovieApiResponse;
import org.example.homework6.model.SimpleCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/home")
public class MovieController {
    SimpleCache cache = new SimpleCache(60 * 1000);
    private final RestTemplate restTemplate = new RestTemplate();

    private String generateCacheKey(String title, Integer page, Integer size) {
        try {
            String input = String.format("%s:%d:%d", title, page, size);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при создании хэша", e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String title,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        title = title.trim().toUpperCase();

        MovieApiResponse cached = cache.get(generateCacheKey(title, page, size));
        if (cached != null) {
            System.out.println("Запрос из кэша: " + title + ", страница: " + page + ", размер: " + size);
            return ResponseEntity.ok(cached);
        }
        System.out.println("Запрос из API: " + title + ", страница: " + page);
        MovieApiResponse result = new MovieApiResponse();
        ResponseEntity<MovieApiResponse> response;

        String url = "https://www.omdbapi.com/?s=" + title + "&apiKey=a9be10a2&page=" + page;
        System.out.println("Отправляется запрос: " + url);
        response = restTemplate.getForEntity(url, MovieApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            result = response.getBody();

            assert result != null;
            int pagesToGo = size / 10;

            for (int i = page + 1; i <= page + pagesToGo; i++) {
                url = "https://www.omdbapi.com/?s=" + title + "&apiKey=a9be10a2&page=" + i;
                System.out.println("Отправляется запрос: " + url);
                response = restTemplate.getForEntity(url, MovieApiResponse.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    MovieApiResponse body = response.getBody();
                    assert body != null;
                    if (i + 1 == page + pagesToGo) {
                        int remaining = size - result.search.size();
                        int endIndex = Math.min(remaining, body.search.size());
                        if (endIndex > 0) {
                            result.search.addAll(body.search.subList(0, endIndex));
                        }
                    } else {
                        result.search.addAll(body.search);
                    }
                }
            }
        }

        cache.put(generateCacheKey(title, page, size), result);
        System.out.println("Save in cached!!!");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/cache/info")
    public ResponseEntity<?> getCacheInfo() {
        return ResponseEntity.ok("Время жизни: " + cache.getTimeToLive() + ", Размер: " + cache.size());
    }

    @DeleteMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        cache.clear();
        return ResponseEntity.ok("Кэш очищен");
    }

    @PutMapping("/cache/ttl")
    public ResponseEntity<?> changeTimeToLive(@RequestParam Integer timeToLive) {
        cache.setTimeToLive(timeToLive);
        return ResponseEntity.ok("Время жизни изменено");
    }
}
