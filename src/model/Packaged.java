package model;
import java.io.*;
import java.time.LocalDate;

public class Packaged extends Product implements Edible, Serializable {

    private static final long serialVersionUID = 1L;
    private static final String COUNTER_FILE = "PackagedCounter.dat";
    private String type;
    private boolean imported;
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

    public Packaged(String description, int stock, float price, float porcentage, boolean available, String type, boolean imported, LocalDate expirationDate, int calories, float discount) {
    super(generateId(), description, stock, price, porcentage, available, discount);

    if (type == null || type.isEmpty()) {
        throw new IllegalArgumentException("El tipo no puede ser nulo ni vacío");
    }
    if (expirationDate == null) {
        throw new IllegalArgumentException("La fecha de vencimiento no puede ser nula");
    }
    if (calories < 0) {
        throw new IllegalArgumentException("Las calorías no pueden ser negativas");
    }

    this.type = type;
    this.imported = imported;
    this.expirationDate = expirationDate;
    this.calories = calories;
    saveInstanceCounter();
}

    private static void saveInstanceCounter() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo del contador: " + e.getMessage());
        }
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
        return
                "Tipo=' " + type + '\'' + System.lineSeparator() +
                "Importado= " + imported + System.lineSeparator() +
                "Fecha de vencimiento= " + expirationDate + System.lineSeparator() +
                "Calorías= " + calories + System.lineSeparator()
                + super.toString() + System.lineSeparator();
    }

}
