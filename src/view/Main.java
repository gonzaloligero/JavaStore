package view;
import model.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

import static model.Handler.getFloatInput;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        while (true) {
            System.out.println("Menú Principal:");
            System.out.println("1. EMPAQUETADOS");
            System.out.println("2. BEBIBLES");
            System.out.println("3. LIMPIEZA");
            System.out.println("4. VENTA GENERAL");
            System.out.println("5. PRODUCTOS COMESTIBLES NO IMPORTADOS");
            System.out.println("6. SALIR");

            int choice = getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    Menú.mostrarMenúEmpaquetados();
                    model.Screen.clearScreen();
                    break;
                case 2:
                    Menú.mostrarMenúBebibles();
                    break;
                case 3:
                    Menú.mostrarMenúLimpieza();
                    break;
                case 4:
                    Menú.generalSell();
                    return;
                case 5:
                    float porcentajeDescuento = getFloatInput("Ingresa el porcentaje de descuento: ");
                    List<Product> productos = Utils.obtenerProductosComestibles("Packaged.dat", "Drinkable.dat", porcentajeDescuento);
                    List<String> comestibles = Utils.obtenerComestiblesConMenorDescuento(productos, porcentajeDescuento);
                    System.out.println("Productos comestibles no importados con descuento menor a " + porcentajeDescuento + "%:");
                    comestibles.forEach(System.out::println);
                    break;
                case 6: System.out.println("Saliendo del sistema.");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");

            }
        }
    }


    static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingresa un número válido.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
}
