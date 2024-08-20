package model;

import java.io.*;

public class Cleaner extends Product {
    public enum ApplicationType {
        COCINA, BAÑO, ROPA, MULTIUSO
    }
    private static final String COUNTER_FILE = "CleanerCounter.dat";

    static {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COUNTER_FILE))) {
            instance = (int) ois.readObject();
        } catch (FileNotFoundException e) {
            instance = 0;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo del contador: " + e.getMessage());
            instance = 0;
        }
    }

    private static int instance = 0;

    private ApplicationType applicationType;

    public Cleaner(String id, String description, int stock, float price, float porcentage, boolean available, ApplicationType applicationType, float discount) {
        super(generateId(), description, stock, price, porcentage, available, discount);
        this.applicationType = applicationType;
        saveInstanceCounter();
    }


    public static String generateId() {
        instance++;
        return String.format("AZ%03d", instance);
    }

    private static void saveInstanceCounter() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo del contador: " + e.getMessage());
        }
    }

    @Override
    protected boolean validateId(String id) {
        return id.matches("^AZ\\d{3}$");
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    @Override
    public String toString() {
        return
                "Tipo de aplicación= " + applicationType + System.lineSeparator()
                + super.toString();
    }
}
