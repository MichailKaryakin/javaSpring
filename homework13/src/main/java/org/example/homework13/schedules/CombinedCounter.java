package org.example.homework13.schedules;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CombinedCounter {

    private int counter = 0;

    @Scheduled(fixedRate = 2000)
    public void incrementCounter() {
        counter++;
        System.out.println("Счётчик увеличен: " + counter);
    }

    @Scheduled(fixedDelay = 5000)
    public void printCounter() {
        System.out.println("Текущее значение счётчика: " + counter);
    }
}
