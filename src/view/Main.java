package view;

import model.*;  // Asegúrate de importar tus clases aquí
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Crear un producto empaquetado");
            System.out.println("2. Crear una bebida");
            System.out.println("3. Crear un producto de limpieza");
            System.out.println("4. Mostrar productos");
            System.out.println("5. Salir");

            int choice = getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    createPackagedProduct();
                    break;
                case 2:
                    createDrinkableProduct();
                    break;
                case 3:
                    createCleanerProduct();
                    break;
                case 4:
                    // showProducts();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingresa un número válido.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static float getFloatInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextFloat()) {
            System.out.println("Por favor ingresa un número decimal válido.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextFloat();
    }

    private static LocalDate getLocalDateInput(String prompt) {
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

    private static void createPackagedProduct() {
        System.out.println("Crear producto empaquetado:");
        String description = getStringInput("Descripción: ");
        int stock = getIntInput("Stock: ");
        float price = getFloatInput("Precio: ");
        float porcentage = getFloatInput("Porcentaje: ");
        boolean available = getIntInput("Disponible (1 para sí, 0 para no): ") == 1;
        String type = getStringInput("Tipo: ");
        boolean imported = getIntInput("Importado (1 para sí, 0 para no): ") == 1;
        LocalDate expirationDate = getLocalDateInput("Fecha de vencimiento (dd/MM/yyyy): ");
        int calories = getIntInput("Calorías: ");
        float discount = getFloatInput("Descuento (%): ");

        Packaged packaged = new Packaged(description, stock, price, porcentage, available, type, imported, expirationDate, calories, discount);
        System.out.println("Producto empaquetado creado: " + packaged);
    }



    private static void createDrinkableProduct() {
        System.out.println("Crear bebida:");
        String description = getStringInput("Descripción: ");
        int stock = getIntInput("Stock: ");
        float price = getFloatInput("Precio: ");
        float porcentage = getFloatInput("Porcentaje: ");
        boolean available = getIntInput("Disponible (1 para sí, 0 para no): ") == 1;
        float alcoholicGraduation = getFloatInput("Graduación alcohólica: ");
        boolean imported = getIntInput("Importado (1 para sí, 0 para no): ") == 1;
        LocalDate expirationDate = getLocalDateInput("Fecha de vencimiento (yyyy-MM-dd): ");
        int calories = getIntInput("Calorías: ");
        float discount = getFloatInput("Descuento (%): ");

        Drinkable drinkable = new Drinkable(description, stock, price, porcentage, available, alcoholicGraduation, imported, expirationDate, calories, discount);
        System.out.println("Bebida creada: " + drinkable);
    }

    private static void createCleanerProduct() {
        System.out.println("Crear producto de limpieza:");
        String description = getStringInput("Descripción: ");
        int stock = getIntInput("Stock: ");
        float price = getFloatInput("Precio: ");
        float porcentage = getFloatInput("Porcentaje: ");
        boolean available = getIntInput("Disponible (1 para sí, 0 para no): ") == 1;
        String applicationTypeStr = getStringInput("Tipo de aplicación (COCINA, BAÑO, ROPA, MULTIUSO): ");
        Cleaner.ApplicationType applicationType;

        try {
            applicationType = Cleaner.ApplicationType.valueOf(applicationTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de aplicación no válido. Usando COCINA por defecto.");
            applicationType = Cleaner.ApplicationType.COCINA;
        }

        float discount = getFloatInput("Descuento (%): ");

        Cleaner cleaner = new Cleaner(description, stock, price, porcentage, available, applicationType, discount);
        // Agregar a una lista o base de datos de productos si es necesario
        System.out.println("Producto de limpieza creado: " + cleaner);
    }
}
