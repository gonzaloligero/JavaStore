// TestTienda.java
package view;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class TestTienda {
    private static float saldoCaja = 1000.0f;

    public static void main(String[] args) {

        List<Product> productos = new ArrayList<>();
        productos.add(new Packaged("Arroz", 50, 10.0f, 5, false, "Granos", false, null, 0, 0.0f));
        productos.add(new Packaged("Arroz", 50, 10.0f, 5, false, "Granos", false, null, 0, 0.0f));
        productos.add(new Packaged("Fideos", 30, 20.0f, 10, false, "Pasta", false, null, 0, 0.0f));
        productos.add(new Packaged("Lentejas", 40, 15.0f, 7, false, "Legumbres", false, null, 0, 0.0f));
        productos.add(new Packaged("Galletas", 25, 5.0f, 20, false, "Snacks", false, null, 0, 0.0f));
        productos.add(new Packaged("Cereal", 60, 12.0f, 8, false, "Desayuno", false, null, 0, 0.0f));
        productos.add(new Packaged("Arroz Integral", 50, 11.0f, 5, false, "Granos", false, null, 0, 0.0f));
        productos.add(new Packaged("Macarrones", 30, 21.0f, 10, false, "Pasta", false, null, 0, 0.0f));
        productos.add(new Packaged("Garbanzos", 40, 16.0f, 7, false, "Legumbres", false, null, 0, 0.0f));
        productos.add(new Packaged("Chocolates", 25, 6.0f, 20, false, "Snacks", false, null, 0, 0.0f));
        productos.add(new Packaged("Avena", 60, 13.0f, 8, false, "Desayuno", false, null, 0, 0.0f));
        productos.add(new Drinkable("Vino", 20, 100.0f, 15, false, 12, false, null, 0, 0.0f));
        productos.add(new Drinkable("Cerveza", 10, 50.0f, 10, true, 5, false, null, 0, 0.0f));
        productos.add(new Drinkable("Jugo", 30, 20.0f, 5, false, 0, false, null, 0, 0.0f));
        productos.add(new Drinkable("Agua", 50, 10.0f, 2, false, 0, false, null, 0, 0.0f));
        productos.add(new Drinkable("Refresco", 40, 25.0f, 8, false, 0, false, null, 0, 0.0f));


        realizarCompra(productos.get(0), 5);
        realizarVenta(productos.get(1), 10);
        realizarCompra(productos.get(2), 2);
        realizarVenta(productos.get(3), 5);


        imprimirEstadoProductos(productos);
        System.out.println("Saldo final en la caja: " + saldoCaja);
    }

    private static void realizarCompra(Product producto, int cantidad) {
        float costoTotal = producto.getPrice() * cantidad;
        if (saldoCaja >= costoTotal) {
            producto.setStock(producto.getStock() + cantidad);
            saldoCaja -= costoTotal;
            System.out.println("Compra realizada: " + cantidad + " unidades de " + producto.getDescription());
            imprimirEstadoProducto(producto);
        } else {
            System.out.println("Saldo insuficiente para realizar la compra de " + producto.getDescription());
        }
    }

    private static void realizarVenta(Product producto, int cantidad) {
        if (producto.getStock() >= cantidad) {
            float ingresoTotal = producto.calculateFinalPrice() * cantidad;
            producto.setStock(producto.getStock() - cantidad);
            saldoCaja += ingresoTotal;
            System.out.println("Venta realizada: " + cantidad + " unidades de " + producto.getDescription());
            imprimirEstadoProducto(producto);
        } else {
            System.out.println("Stock insuficiente para realizar la venta de " + producto.getDescription());
        }
    }

    private static void imprimirEstadoProducto(Product producto) {
        System.out.println("Estado del producto: " + producto.getDescription());
        System.out.println("Stock: " + producto.getStock());
        System.out.println("Precio de venta: " + producto.calculateFinalPrice());
        System.out.println("Precio de compra: " + producto.getPrice());
    }

    private static void imprimirEstadoProductos(List<Product> productos) {
        for (Product producto : productos) {
            imprimirEstadoProducto(producto);
        }
    }
}