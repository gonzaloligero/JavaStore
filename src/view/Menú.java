package view;

import model.FilePackaged;
import model.Handler;
import model.Packaged;
import model.Store;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menú {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FilePackaged filePackaged = new FilePackaged();
    private static float Balance = 10000; // Le coloqué este saldo como base para comprar productos.
    // A medida que compramos se va perdiendo y cuando vendemos aumenta.

    static Store tienda = new Store("Qué Tienda", 10000, 5000);

    public static void mostrarMenúEmpaquetados() {
        while (true) {
            System.out.println("Menú EMPAQUETADOS:");
            System.out.println("1. COMPRAR");
            System.out.println("2. VENDER");
            System.out.println("3. LISTAR");
            System.out.println("4. EDITAR");
            System.out.println("5. NUEVO");
            System.out.println("6. VOLVER");

            int choice = Main.getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    handleBuyPackaged();
                    break;
                case 2:
                    handleSellPackaged();
                    break;
                case 3:
                    handleListPackaged();
                    break;
                case 4:
                    handleEditPackaged();
                    break;
                case 5:
                    handleNewPackaged();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    //Estos métodos organizan todos los productos empaquetados.
    //Seguí la dinámica de dejarlos en inglés por lo que comenté en el README del repo.
    private static void handleBuyPackaged() {
        System.out.println("Comprar producto empaquetado.");

        List<Packaged> packagedProducts = filePackaged.readAllPackaged();
        if (packagedProducts.isEmpty()) {
            System.out.println("No hay productos empaquetados disponibles.");
            return;
        }

        System.out.println("Productos disponibles:");
        for (Packaged packaged : packagedProducts) {
            System.out.printf("Código: %s, Descripción: %s, Precio: %.2f%n",
                    packaged.getId(), packaged.getDescription(), packaged.getPrice());
        }

        String id = Handler.getStringInput("Código del producto (ABxxx): ");
        Packaged packaged = findPackagedById(id);

        if (packaged == null) {
            System.out.println("No existe ese código de producto.");
            return;
        }

        System.out.printf("Detalles del producto seleccionado:%n");
        System.out.printf("Código: %s%n", packaged.getId());
        System.out.printf("Descripción: %s%n", packaged.getDescription());
        System.out.printf("Precio: %.2f%n", packaged.getPrice());
        System.out.printf("Stock disponible: %d%n", packaged.getStock());

        int quantity = Handler.getIntInput("Cantidad a comprar: ");
        float totalPrice = packaged.getPrice() * quantity;

        if (quantity <= 0 || totalPrice <= 0) {
            System.out.println("Cantidad o precio no válidos.");
            return;
        }

        if (quantity > packaged.getStock()) {
            System.out.println("Cantidad solicitada excede el stock disponible.");
            return;
        }

        if (totalPrice > Balance) {
            System.out.println("El producto no podrá ser agregado a la  tienda por saldo insuficiente en la caja");
            return;
        }

        packaged.setStock(packaged.getStock() - quantity);
        Balance -= totalPrice;
        filePackaged.editPackaged(id, packaged);

        System.out.println("Compra realizada con éxito. Saldo restante: " + Balance);
    }

    private static Packaged findPackagedById(String id) {
        List<Packaged> packagedProducts = filePackaged.readAllPackaged();
        for (Packaged packaged : packagedProducts) {
            if (packaged.getId().equals(id)) {
                return packaged;
            }
        }
        return null;
    }

    private static void handleSellPackaged() {
        System.out.println("Vender productos empaquetados.");

        List<Packaged> packagedProducts = filePackaged.readAllPackaged();
        if (packagedProducts.isEmpty()) {
            System.out.println("No hay productos empaquetados disponibles.");
            return;
        }

        System.out.println("Productos disponibles:");
        for (Packaged packaged : packagedProducts) {
            System.out.printf("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d%n",
                    packaged.getId(), packaged.getDescription(), packaged.getPrice(), packaged.getStock());
        }

        String id = Handler.getStringInput("Código del producto (ABxxx): ");
        Packaged packaged = findPackagedById(id);

        if (packaged == null) {
            System.out.println("No existe ese código de producto.");
            return;
        }

        System.out.printf("Detalles del producto seleccionado:%n");
        System.out.printf("Código: %s%n", packaged.getId());
        System.out.printf("Descripción: %s%n", packaged.getDescription());
        System.out.printf("Precio: %.2f%n", packaged.getPrice());
        System.out.printf("Stock disponible: %d%n", packaged.getStock());

        int quantity = Handler.getIntInput("Cantidad a vender: ");
        float totalPrice = packaged.getPrice() * quantity;

        if (quantity <= 0 || totalPrice <= 0) {
            System.out.println("Cantidad o precio no válidos.");
            return;
        }

        if (quantity > packaged.getStock()) {
            System.out.println("Cantidad solicitada excede el stock disponible.");
            return;
        }

        packaged.setStock(packaged.getStock() - quantity);
        Balance += totalPrice;
        filePackaged.editPackaged(id, packaged);

        System.out.println("Venta realizada con éxito. Saldo actualizado: " + Balance);
    }

    private static void handleListPackaged() {
        List<Packaged> packagedProducts = filePackaged.readAllPackaged();
        System.out.println("Lista de productos empaquetados:");
        for (Packaged packaged : packagedProducts) {
            System.out.println(packaged);
        }
    }

    private static void handleEditPackaged() {
        System.out.println("Editar productos empaquetados.");

        String idToEdit = Handler.getStringInput("Ingresa el código del producto a editar (e.g., AB001): ");

        List<Packaged> packagedProducts = filePackaged.readAllPackaged();

        Packaged productToEdit = null;
        for (Packaged packaged : packagedProducts) {
            if (packaged.getId().equals(idToEdit)) {
                productToEdit = packaged;
                break;
            }
        }

        if (productToEdit == null) {
            System.out.println("No existe ese código de producto.");
            return;
        }
        String description = Handler.getStringInput("Descripción (" + productToEdit.getDescription() + "): ");
        int stock = Handler.getIntInput("Stock (" + productToEdit.getStock() + "): ");
        float price = Handler.getFloatInput("Precio (" + productToEdit.getPrice() + "): ");
        float percentage = Handler.getFloatInput("Porcentaje (" + productToEdit.getPorcentage() + "): ");
        boolean available = Handler.getIntInput("Disponible (1 para sí, 0 para no) (" + (productToEdit.isAvailable() ? "1" : "0") + "): ") == 1;
        String type = Handler.getStringInput("Tipo (" + productToEdit.getType() + "): ");
        boolean imported = Handler.getIntInput("Importado (1 para sí, 0 para no) (" + (productToEdit.isImported() ? "1" : "0") + "): ") == 1;
        LocalDate expirationDate = Handler.getLocalDateInput("Fecha de vencimiento (yyyy-MM-dd) (" + productToEdit.getExpirationDate() + "): ");
        int calories = Handler.getIntInput("Calorías (" + productToEdit.getCalories() + "): ");
        float discount = Handler.getFloatInput("Descuento (%) (" + productToEdit.getDiscountPercentage() + "): ");
        Packaged updatedPackaged = new Packaged(
                description.isEmpty() ? productToEdit.getDescription() : description,
                stock,
                price,
                percentage,
                available,
                type.isEmpty() ? productToEdit.getType() : type,
                imported,
                expirationDate,
                calories,
                discount
        );

        for (int i = 0; i < packagedProducts.size(); i++) {
            if (packagedProducts.get(i).getId().equals(idToEdit)) {
                packagedProducts.set(i, updatedPackaged);
                break;
            }
        }
        filePackaged.savePackagedToFile(packagedProducts);

        System.out.println("Producto empaquetado actualizado exitosamente.");
    }

    private static void handleNewPackaged() {
        System.out.println("Nuevo producto empaquetado.");

        String description = Handler.getStringInput("Descripción: ");
        int stock = Handler.getIntInput("Stock: ");
        float price = Handler.getFloatInput("Precio: ");
        float percentage = Handler.getFloatInput("Porcentaje: ");
        boolean available = Handler.getIntInput("Disponible (1 para sí, 0 para no): ") == 1;
        String type = Handler.getStringInput("Tipo: ");
        boolean imported = Handler.getIntInput("Importado (1 para sí, 0 para no): ") == 1;
        LocalDate expirationDate = Handler.getLocalDateInput("Fecha de vencimiento (yyyy-MM-dd): ");
        int calories = Handler.getIntInput("Calorías: ");
        float discount = Handler.getFloatInput("Descuento (%): ");

        Packaged newPackaged = new Packaged(
                description,
                stock,
                price,
                percentage,
                available,
                type,
                imported,
                expirationDate,
                calories,
                discount
        );

        List<Packaged> packagedProducts = filePackaged.readAllPackaged();
        int currentStockTotal = packagedProducts.stream()
                .mapToInt(Packaged::getStock)
                .sum();

        if (currentStockTotal + stock > tienda.getStockLimit()) {
            System.out.println("No se puede agregar el producto. El stock total excede el límite permitido de la tienda.");
            return;
        }

        filePackaged.addPackaged(newPackaged);

        System.out.println("Producto empaquetado creado y añadido exitosamente.");
    }



    public static void mostrarMenúBebibles() {
        while (true) {
            System.out.println("Menu BEBIBLES:");
            System.out.println("1. COMPRAR");
            System.out.println("2. VENDER");
            System.out.println("3. LISTAR");
            System.out.println("4. EDITAR");
            System.out.println("5. VOLVER");

            int choice = Main.getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    System.out.println("Comprar bebidas.");
                    break;
                case 2:
                    System.out.println("Vender bebidas.");
                    break;
                case 3:
                    System.out.println("Listar bebidas.");
                    break;
                case 4:
                    System.out.println("Editar bebidas.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    public static void mostrarMenúLimpieza() {
        while (true) {
            System.out.println("Menu LIMPIEZA:");
            System.out.println("1. COMPRAR");
            System.out.println("2. VENDER");
            System.out.println("3. LISTAR");
            System.out.println("4. EDITAR");
            System.out.println("5. VOLVER");

            int choice = Main.getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    System.out.println("Comprar productos de limpieza.");
                    break;
                case 2:
                    System.out.println("Vender productos de limpieza.");
                    break;
                case 3:
                    System.out.println("Listar productos de limpieza.");
                    break;
                case 4:
                    System.out.println("Editar productos de limpieza.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingresa un número válido.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
}
