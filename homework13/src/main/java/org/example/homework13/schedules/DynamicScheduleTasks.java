package org.example.homework13.schedules;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DynamicScheduleTasks {

    @Scheduled(cron = "${morning.task.cron}")
    public void morningTask() {
        System.out.println(
                "Утренняя задача: " + LocalTime.now()
        );
    }

    @Scheduled(cron = "${afternoon.task.cron}")
    public void afternoonTask() {
        System.out.println(
                "Дневная задача: " + LocalTime.now()
        );
    }

    @Scheduled(cron = "${evening.task.cron}")
    public void eveningTask() {
        System.out.println(
                "Вечерняя задача: " + LocalTime.now()
        );
    }
}

