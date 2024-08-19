package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Handler {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingresa un número válido.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    public static float getFloatInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextFloat()) {
            System.out.println("Por favor ingresa un número decimal válido.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextFloat();
    }

    public static LocalDate getLocalDateInput(String prompt) {
        System.out.print(prompt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            String dateStr = scanner.next();
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no válida. Usa el formato yyyy-MM-dd.");
            }
        }
    }
}
