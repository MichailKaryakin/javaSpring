import java.util.Scanner;

/**
 * Пользователь вводит число (1 - 3999)
 * I - 1
 * V - 5
 * X - 10
 * L - 50
 * C - 100
 * D - 500
 * M - 1000
 * Пример  10 -> x
 * 6 -> VI
 */
public class RomanNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        System.out.println(toRoman(number));
    }

    public static String toRoman(int number) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder romanNumber = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                romanNumber.append(symbols[i]);
                number -= values[i];
            }
        }

        return romanNumber.toString();
    }
}
