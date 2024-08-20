package model;
import java.time.LocalDate;
public interface Edible {

    LocalDate getExpirationDate();
    void setExpirationDate(LocalDate expirationDate);
    int getCalories();
    void setCalories(int calories);
    default void validateProfitMargin(float percentage) {
        if (percentage > 20) {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede superar el 20% para productos comestibles.");
        }
    }
}
