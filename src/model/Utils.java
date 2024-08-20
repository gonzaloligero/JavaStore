package model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static List<Product> obtenerProductosComestibles(String packagedFile, String drinkableFile, float porcentajeDescuento) {
        List<Product> productos = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(packagedFile))) {
            while (true) {
                try {
                    Packaged packaged = (Packaged) ois.readObject();
                    if (!packaged.isImported() && packaged.getDiscountPercentage() < porcentajeDescuento) {
                        productos.add(packaged);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo de productos empaquetados: " + e.getMessage());
        }


        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(drinkableFile))) {
            while (true) {
                try {
                    Drinkable drinkable = (Drinkable) ois.readObject();
                    if (!drinkable.isImported() && drinkable.getDiscountPercentage() < porcentajeDescuento) {
                        productos.add(drinkable);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo de productos bebibles: " + e.getMessage());
        }

        return productos;
    }

    public static List<String> obtenerComestiblesConMenorDescuento(List<Product> products, float porcentajeDescuento) {
        return products.stream()
                .filter(product -> product instanceof Edible)
                .filter(product -> !((Drinkable) product).isImported())
                .filter(product -> product.getDiscountPercentage() < porcentajeDescuento)
                .sorted((p1, p2) -> Float.compare(p1.calculateFinalPrice(), p2.calculateFinalPrice()))
                .map(product -> product.getDescription().toUpperCase())
                .collect(Collectors.toList());
    }
}