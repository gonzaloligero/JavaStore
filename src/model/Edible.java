package model;
import java.time.LocalDate;
public interface Edible {

    LocalDate getExpirationDate();
    void setExpirationDate(LocalDate expirationDate);
    int getCalories();
    void setCalories(int calories);
}
