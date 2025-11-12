package org.example.homework7.services;

import jakarta.annotation.PostConstruct;
import org.example.homework7.dto.UserListResponse;
import org.example.homework7.mapper.UserMapper;
import org.example.homework7.model.RandomUserApiResponse;
import org.example.homework7.model.SimpleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RandomUserService {

    private final SimpleCache cache;
    private final RestTemplate restTemplate;

    @Value("${random.cache.ttl:}")
    private long ttl;

    @Autowired
    public RandomUserService(SimpleCache cache, RestTemplate restTemplate) {
        this.cache = cache;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void setTtl() {
        cache.setTimeToLive(ttl);
    }

    /**
     * Получить список случайных пользователей.
     */
    public ResponseEntity<?> getRandomUsers(int count) {
        return fetchUsers("https://randomuser.me/api/?results=" + count,
                "users_random_" + count,
                Map.of("results", String.valueOf(count)));
    }

    /**
     * Получить пользователей по полу.
     */
    public ResponseEntity<?> getByGender(int count, String gender) {
        return fetchUsers("https://randomuser.me/api/?results=" + count + "&gender=" + gender,
                "users_gender_" + gender + "_" + count,
                Map.of("gender", gender, "results", String.valueOf(count)));
    }

    /**
     * Получить пользователей по национальности.
     */
    public ResponseEntity<?> getByNationality(int count, String nat) {
        return fetchUsers("https://randomuser.me/api/?results=" + count + "&nat=" + nat,
                "users_nat_" + nat + "_" + count,
                Map.of("nat", nat, "results", String.valueOf(count)));
    }

    /**
     * Общий метод для запроса и кэширования пользователей.
     */
    private ResponseEntity<?> fetchUsers(String url, String cacheKey, Map<String, String> filters) {
        UserListResponse cached = cache.get(cacheKey);
        if (cached != null) {
            System.out.println("Данные из кэша: " + cacheKey);
            return ResponseEntity.ok(cached);
        }

        ResponseEntity<RandomUserApiResponse> response =
                restTemplate.getForEntity(url, RandomUserApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            UserListResponse userList = UserMapper.fromApiResponse(response.getBody(), filters);

            cache.put(cacheKey, userList);
            System.out.println("Данные сохранены в кэш: " + cacheKey);

            return ResponseEntity.ok(userList);
        }

        System.out.println("Ошибка получения данных: " + response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body("Ошибка при получении данных");
    }

    /**
     * Статистика кэша.
     */
    public ResponseEntity<?> getCacheStats() {
        return ResponseEntity.ok(cache.getStats());
    }

    /**
     * Очистить кэш.
     */
    public ResponseEntity<?> clearCache() {
        cache.clear();
        System.out.println("Кэш очищен");
        return ResponseEntity.ok("Кэш очищен");
    }
}
