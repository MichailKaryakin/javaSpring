import java.util.Scanner;

public class Console {
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (running) {
            System.out.println("""
                    1. Показать всех студентов
                    2. Найти студента по ID
                    3. Добавить нового студента
                    4. Обновить данные студента
                    5. Удалить студента
                    6. Поиск по имени/фамилии
                    7. Поиск по email
                    8. Фильтр по возрасту
                    0. Выход
                    """);
            int operationId = scanner.nextInt();
            if (operationId == 0) {
                running = false;
            } else if (operationId > 0 && operationId <= 8) {
                Operation operation = new Operation(operationId);
                String result = operation.implement();
                System.out.println(result);
            } else {
                System.out.println("Такого пункта не существует, повторите ввод");
            }
        }
    }
}
