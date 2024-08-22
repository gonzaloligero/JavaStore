package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String description;
    private int stock;
    private float price;
    private float porcentage;
    private boolean available;
    private float discount;


   public Product(String id, String description, int stock, float price, float porcentage, boolean available, float discount) {
    if (id == null || id.isEmpty() || !validateId(id)) {
        throw new IllegalArgumentException("ID de producto inválido: " + id);
    }
    if (description == null || description.isEmpty()) {
        throw new IllegalArgumentException("La descripción no puede ser nula ni vacía");
    }
    if (stock < 0) {
        throw new IllegalArgumentException("El stock no puede ser negativo");
    }
    if (price < 0) {
        throw new IllegalArgumentException("El precio no puede ser negativo");
    }
    if (porcentage < 0 || porcentage > 100) {
        throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
    }
    if (discount < 0 || discount > 100) {
        throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
    }
    if (!isNumeric(String.valueOf(stock))) {
        throw new IllegalArgumentException("El stock debe ser un número");
    }
    if (!isNumeric(String.valueOf(price))) {
        throw new IllegalArgumentException("El precio debe ser un número");
    }
    if (!isNumeric(String.valueOf(porcentage))) {
        throw new IllegalArgumentException("El porcentaje debe ser un número");
    }
    if (!isNumeric(String.valueOf(discount))) {
        throw new IllegalArgumentException("El descuento debe ser un número");
    }
    this.id = id;
    this.description = description;
    this.stock = stock;
    this.price = price;
    this.porcentage = porcentage;
    this.available = available;
    this.discount = discount;
}

private boolean isNumeric(String str) {
    try {
        Float.parseFloat(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

    public Product() {
        this.id = "A0000";
        this.description = "empty";
        this.stock = 0;
        this.price = 0;
        this.porcentage = 0;
        this.available = false;
        this.discount = 0;
    }

    protected abstract boolean validateId(String id);

    public void setId(String id) {
        if (validateId(id)) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(float porcentage) {
        this.porcentage = porcentage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public float getDiscountPercentage() {
        return discount;
    }

    public void setDiscountPercentage(float discount) {
        this.discount = discount;
    }

    public float calculateFinalPrice() {
        return getPrice();
    }

    @Override
    public String toString() {
        return
                "ID= '" + id + '\'' + System.lineSeparator() +
                "Descripción= '" + description + '\'' + System.lineSeparator() +
                "Stock= " + stock + System.lineSeparator() +
                "Precio= " + price + System.lineSeparator() +
                "Porcentaje= " + porcentage + System.lineSeparator() +
                "Disponible=" + available + System.lineSeparator() +
                "Descuento aplicado= " + discount + System.lineSeparator();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return stock == product.stock && Float.compare(price, product.price) == 0 && Float.compare(porcentage, product.porcentage) == 0 && available == product.available && Float.compare(discount, product.discount) == 0 && Objects.equals(id, product.id) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, stock, price, porcentage, available, discount);
    }
}
