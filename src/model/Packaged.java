package model;
import java.time.LocalDate;

public class Packaged extends Product implements Edible {

    private String type;
    private boolean imported;
    private LocalDate expirationDate;
    private int calories;
    private static int instance = 0;

    public Packaged(String description, int stock, float price, float porcentage, boolean available, String type, boolean imported, LocalDate expirationDate, int calories, float discount) {
        super(generateId(), description, stock, price, porcentage, available,discount);
        this.type = type;
        this.imported = imported;
        this.expirationDate = expirationDate;
        this.calories = calories;
    }

    private static String generateId() {
        instance++;
        return String.format("AB%03d", instance);
    }

    @Override
    protected boolean validateId(String id) {
        return id.matches("^AB\\d{3}$");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Packaged{" +
                "type='" + type + '\'' +
                ", imported=" + imported +
                ", expirationDate=" + expirationDate +
                ", calories=" + calories +
                "} " + super.toString();
    }
}
