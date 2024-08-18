package model;

import java.time.LocalDate;

public class Drinkable extends Product implements Edible {

    private float alcoholicGraduation;
    private boolean imported;
    private LocalDate expirationDate;
    private int calories;

    private static int instance = 0;

    public Drinkable(String description, int stock, float price, float porcentage, boolean available, float alcoholicGraduation, boolean imported, LocalDate expirationDate, int calories, float discount) {
        super(generateId(), description, stock, price, porcentage, available, discount);
        this.alcoholicGraduation = alcoholicGraduation;
        this.imported = imported;
        this.expirationDate = expirationDate;
        this.calories = calories;
    }

    private static String generateId() {
        instance++;
        return String.format("AC%03d", instance);
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
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Drinkable{" +
                "alcoholicGraduation=" + alcoholicGraduation +
                ", imported=" + imported +
                ", expirationDate=" + expirationDate +
                ", calories=" + calories +
                "} " + super.toString();
    }
}
