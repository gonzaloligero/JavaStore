package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileDrinkable {

    private final String fileName = "Drinkable.dat";


    public List<Drinkable> readAllDrinkable() {
        List<Drinkable> drinkableProducts = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            drinkableProducts = (List<Drinkable>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo al guardar productos bebibles.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return drinkableProducts;
    }

    public void addDrinkable(Drinkable drinkable) {
        List<Drinkable> drinkableProducts = readAllDrinkable();
        drinkableProducts.add(drinkable);
        saveDrinkableToFile(drinkableProducts);
    }

    public void editDrinkable(String id, Drinkable updatedDrinkable) {
        List<Drinkable> drinkableProducts = readAllDrinkable();

        boolean found = false;
        for (int i = 0; i < drinkableProducts.size(); i++) {
            if (drinkableProducts.get(i).getId().equals(id)) {
                drinkableProducts.set(i, updatedDrinkable);
                found = true;
                break;
            }
        }

        if (found) {
            saveDrinkableToFile(drinkableProducts);
            System.out.println("Producto bebible actualizado exitosamente.");
        } else {
            System.out.println("Producto bebible no encontrado.");
        }
    }

    public void saveDrinkableToFile(List<Drinkable> drinkableProducts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(drinkableProducts);
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }
}

