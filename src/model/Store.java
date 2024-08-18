package model;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public abstract class Store {
    private String name;
    private String stockLimit;
    private float balance;
    private Map<String, Product> stockProducts;

    public Store(String name, String stockLimit, float balance, Map<String, Product> stockProducts) {
        this.name = name;
        this.stockLimit = stockLimit;
        this.balance = balance;
        this.stockProducts = stockProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockLimit() {
        return stockLimit;
    }

    public void setStockLimit(String stockLimit) {
        this.stockLimit = stockLimit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Map<String, Product> getStockProducts() {
        return stockProducts;
    }

    public void setStockProducts(Map<String, Product> stockProducts) {
        this.stockProducts = stockProducts;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", stockLimit='" + stockLimit + '\'' +
                ", balance=" + balance +
                ", stockProducts=" + stockProducts +
                '}';
    }

}
