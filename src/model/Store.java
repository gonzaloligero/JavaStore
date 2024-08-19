package model;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class Store {
    private String name;
    private int stockLimit;
    private float balance;

    public Store(String name, int stockLimit, float balance) {
        this.name = name;
        this.stockLimit = stockLimit;
        this.balance = balance;
    }

    public Store() {
        this.name = "Qué Tienda";
        this.stockLimit = 10000;
        this.balance = 5000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStockLimit() {
        return stockLimit;
    }

    public void setStockLimit(int stockLimit) {
        this.stockLimit = stockLimit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", stockLimit='" + stockLimit + '\'' +
                ", balance=" + balance;
    }

}
