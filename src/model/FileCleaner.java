package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCleaner {

    private final String fileName = "Cleaner.dat";

    public List<Cleaner> readAllCleaner() {
        List<Cleaner> cleanerProducts = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            cleanerProducts = (List<Cleaner>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo al guardar productos de limpieza.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return cleanerProducts;
    }

    public void addCleaner(Cleaner cleaner) {
        List<Cleaner> cleanerProducts = readAllCleaner();
        cleanerProducts.add(cleaner);
        saveCleanerToFile(cleanerProducts);
    }

    public void editCleaner(String id, Cleaner updatedCleaner) {
        List<Cleaner> cleanerProducts = readAllCleaner();

        boolean found = false;
        for (int i = 0; i < cleanerProducts.size(); i++) {
            if (cleanerProducts.get(i).getId().equals(id)) {
                cleanerProducts.set(i, updatedCleaner);
                found = true;
                break;
            }
        }

        if (found) {
            saveCleanerToFile(cleanerProducts);
            System.out.println("Producto de limpieza actualizado exitosamente.");
        } else {
            System.out.println("Producto de limpieza no encontrado.");
        }
    }

    public void saveCleanerToFile(List<Cleaner> cleanerProducts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(cleanerProducts);
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }
}