package model;

import java.io.*;
import java.time.LocalDate;

public class Drinkable extends Product implements Edible {

    private float alcoholicGraduation;
    private boolean imported;
    private static final String COUNTER_FILE = "DrinkableCounter.dat";
    private LocalDate expirationDate;
    private int calories;

    private static int instance = 0;

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

    public Drinkable(String description, int stock, float price, float percentage, boolean available, float alcoholicGraduation, boolean imported, LocalDate expirationDate, int calories, float discount) {
        super(generateId(), description, stock, price, percentage, available, discount);
        this.alcoholicGraduation = alcoholicGraduation;
        this.imported = imported;
        this.expirationDate = expirationDate;
        this.calories = calculateCalories(calories);
        validateDiscount(discount);
        saveInstanceCounter();
    }



    private static String generateId() {
        instance++;
        return String.format("AC%03d", instance);
    }

    private static void saveInstanceCounter() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo del contador: " + e.getMessage());
        }
    }

    private void validateDiscount(float discount) {
        if (discount > 10) {
            throw new IllegalArgumentException("El porcentaje de descuento no puede superar el 10% para bebidas.");
        }
    }

    @Override
    public float calculateFinalPrice() {
        float finalPrice = super.calculateFinalPrice();
        if (imported) {
            finalPrice *= 1.12;
        }
        return finalPrice;
    }

    @Override
    protected boolean validateId(String id) {
        return id.matches("^AC\\d{3}$");
    }

    public float getAlcoholicGraduation() {
        return alcoholicGraduation;
    }

    public void setAlcoholicGraduation(float alcoholicGraduation) {
        this.alcoholicGraduation = alcoholicGraduation;
        this.calories = calculateCalories(this.calories);
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public void setCalories(int calories) {
        this.calories = calculateCalories(calories);
    }

    private int calculateCalories(int baseCalories) {
        if (alcoholicGraduation >= 0 && alcoholicGraduation <= 2) {
            return baseCalories;
        } else if (alcoholicGraduation > 2 && alcoholicGraduation <= 4.5) {
            return (int) (baseCalories * 1.25);
        } else {
            return (int) (baseCalories * 1.5);
        }
    }

    @Override
    public String toString() {
        return
                "Graduación alcohólica= " + alcoholicGraduation + System.lineSeparator() +
                        "Importado= " + imported + System.lineSeparator() +
                        "Fecha de vencimiento= " + expirationDate + System.lineSeparator() +
                        "Calorías= " + calories + System.lineSeparator() +
                        super.toString();
    }
}