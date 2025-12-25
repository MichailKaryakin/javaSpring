package org.example.homework13.schedules;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ScheduledTasks {

    private int counter = 0;

    @Scheduled(fixedRate = 3000)
    public void sayHello() {
        System.out.println("Привет, мир!");
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 2000)
    public void counterTask() {
        counter++;
        System.out.println("Счётчик: " + counter);
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void morningTask() {
        System.out.println("Доброе утро!");
    }

    @Scheduled(cron = "0 * * * * *")
    public void everyMinute() {
        System.out.println("Текущее время: " + LocalTime.now());
    }

    @Scheduled(cron = "0,30 * * * * *")
    public void everyThirtySeconds() {
        System.out.println("30 секунд прошло");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void everyMidnight() {
        System.out.println("Полночь!");
    }

    @Scheduled(cron = "0 0 9-17 * * *")
    public void everyWorkingHour() {
        System.out.println("Ещё один прекрасный час!");
    }

    @Scheduled(cron = "0 0 10 * * Mon")
    public void beginningOfWorkingWeek() {
        System.out.println("Начало рабочей недели!");
    }

    @Scheduled(cron = "${task.schedule}")
    public void configurableTask() {
        System.out.println("Что-то выводится");
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void beginningOfTheMonth() {
        System.out.println("Новый месяц начался!");
    }

    @Scheduled(cron = "0 0 12 ? * MON-FRI")
    @Scheduled(cron = "0 30 12 ? * MON-FRI")
    @Scheduled(cron = "0 0 13 ? * MON-FRI")
    public void lunchBreak() {
        System.out.println(
                "Обеденный перерыв: " + LocalTime.now()
        );
    }

    @Scheduled(cron = "0 0,15,30,45 8-19 * * *")
    @Scheduled(cron = "0 0 20 * * *")
    public void everyQuarterOfHour() {
        System.out.println("Четверть часа прошла");
    }

    @Scheduled(cron = "0 0 11 * * 6,0")
    public void weekendCongratulations() {
        System.out.println("Хороших выходных!");
    }

    @Scheduled(cron = "0 0 9 1 1,4,7,10 *")
    public void everyQuarterOfYear() {
        System.out.println("Начало квартала!");
    }

    @Scheduled(cron = "0 0 2-4 * * *")
    public void nightMaintenance() {
        System.out.println("Выполняется ночное обслуживание");
    }

    @Scheduled(cron = "0 0 23 L * *")
    public void lastDayOfMonth() {
        System.out.println("Сегодня последний день месяца!");
    }

    @Scheduled(cron = "0 0 9-18 * * 1-5")
    public void everyHourExcludingDinner() {
        if (LocalTime.now().getHour() != 12 && LocalTime.now().getHour() != 13) {
            System.out.println("Рабочее время");
        }
    }
}
