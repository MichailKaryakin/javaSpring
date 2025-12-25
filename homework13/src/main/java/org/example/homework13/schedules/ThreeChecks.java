package org.example.homework13.schedules;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ThreeChecks {

    private int fastCounter = 0;
    private int mediumCounter = 0;
    private int deepCounter = 0;

    @Scheduled(cron = "*/10 * * * * *")
    public void fastCheck() {
        fastCounter++;
        System.out.println("Быстрая проверка #" + fastCounter);
    }

    @Scheduled(cron = "0 * * * * *")
    public void mediumCheck() {
        mediumCounter++;
        System.out.println("Средняя проверка #" + mediumCounter);
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void deepCheck() {
        deepCounter++;
        System.out.println("Глубокая проверка #" + deepCounter);
    }
}
