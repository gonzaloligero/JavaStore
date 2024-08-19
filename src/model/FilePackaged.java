package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FilePackaged {

    private final String fileName = "Packaged.dat";

    public List<Packaged> readAllPackaged() {
        List<Packaged> packagedProducts = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            packagedProducts = (List<Packaged>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo al guardar productos empaquetados.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return packagedProducts;
    }

    public void addPackaged(Packaged packaged) {
        List<Packaged> packagedProducts = readAllPackaged();
        packagedProducts.add(packaged);
        savePackagedToFile(packagedProducts);
    }

    public void editPackaged(String id, Packaged updatedPackaged) {
        List<Packaged> packagedProducts = readAllPackaged();

        boolean found = false;
        for (int i = 0; i < packagedProducts.size(); i++) {
            if (packagedProducts.get(i).getId().equals(id)) {
                packagedProducts.set(i, updatedPackaged);
                found = true;
                break;
            }
        }

        if (found) {
            savePackagedToFile(packagedProducts);
            System.out.println("Producto empaquetado actualizado exitosamente.");
        } else {
            System.out.println("Producto empaquetado no encontrado.");
        }
    }

    public void savePackagedToFile(List<Packaged> packagedProducts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(packagedProducts);
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }
}
