package org.example.homework7.controller;

import org.example.homework7.services.RandomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class RandomUserController {
    RandomUserService randomUserService;

    @Autowired
    public RandomUserController(RandomUserService randomUserService) {
        this.randomUserService = randomUserService;
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomUsers(@RequestParam int count) {
        return randomUserService.getRandomUsers(count);
    }

    @GetMapping("/random/by-gender")
    public ResponseEntity<?> getByGender(@RequestParam int count, @RequestParam String gender) {
        return randomUserService.getByGender(count, gender);
    }

    @GetMapping("/random/by-nationality")
    public ResponseEntity<?> getByNationality(@RequestParam int count, @RequestParam String nat) {
        return randomUserService.getByNationality(count, nat);
    }

    /**
     * GET запрос на получение данных о кэше
     */
    @GetMapping("/cache/stats")
    public ResponseEntity<?> getCacheStats() {
        return randomUserService.getCacheStats();
    }

    /**
     * POST запрос на очистку кэша
     */
    @PostMapping("/cache/clear")
    public ResponseEntity<?> clearCache() {
        return randomUserService.clearCache();
    }
}
