package view;

import controller.Operation;
import model.Database;

import java.util.Scanner;

public class Console {
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);
    private final Database database;

    public Console(Database database) {
        this.database = database;
    }

    public void run() {
        while (running) {
            printMenu();
            int operationId = askMenuOption();

            if (operationId == 0) {
                running = false;
                System.out.println("Выход из программы...");
                continue;
            }

            if (operationId < 0 || operationId > 8) {
                System.out.println("Такого пункта не существует, повторите ввод");
                continue;
            }

            Operation operation = new Operation(operationId, database);
            System.out.println(operation.implement());
            System.out.println("\n----------------------------------\n");
        }
    }

    private void printMenu() {
        System.out.println("""
                -----------------------------
                Меню:
                1. Показать всех студентов
                2. Найти студента по ID
                3. Добавить нового студента
                4. Обновить данные студента
                5. Удалить студента
                6. Поиск по имени/фамилии
                7. Поиск по email
                8. Фильтр по возрасту
                0. Выход
                -----------------------------
                """);
    }

    private int askMenuOption() {
        System.out.print("Введите номер операции: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Введите корректное число!");
            scanner.nextLine(); // очищаем ввод
        }
        int option = scanner.nextInt();
        scanner.nextLine(); // съедаем перевод строки
        return option;
    }

    public int askId() {
        System.out.print("Введите id студента: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Введите корректное число!");
            scanner.nextLine();
        }
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    public String askName() {
        System.out.print("Введите имя студента: ");
        return scanner.nextLine().trim();
    }

    public String askSurname() {
        System.out.print("Введите фамилию студента: ");
        return scanner.nextLine().trim();
    }

    public int askAge() {
        System.out.print("Введите возраст студента: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Введите корректное число!");
            scanner.nextLine();
        }
        int age = scanner.nextInt();
        scanner.nextLine();
        return age;
    }

    public String askEmail() {
        System.out.print("Введите эл. почту студента: ");
        return scanner.nextLine().trim();
    }

    public String askPhone() {
        System.out.print("Введите телефон студента: ");
        return scanner.nextLine().trim();
    }
}
