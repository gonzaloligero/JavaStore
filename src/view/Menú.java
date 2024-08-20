package view;

import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menú {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FilePackaged filePackaged = new FilePackaged();
    private static final FileDrinkable fileDrinkable = new FileDrinkable();
    private static final FileCleaner fileCleaner = new FileCleaner();
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

    private static Drinkable findDrinkableById(String id) {
        List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
        for (Drinkable drinkable : drinkableProducts) {
            if (drinkable.getId().equals(id)) {
                return drinkable;
            }
        }
        return null;
    }

    public static void mostrarMenúBebibles() {
        while (true) {
            System.out.println("Menu BEBIBLES:");
            System.out.println("1. COMPRAR");
            System.out.println("2. VENDER");
            System.out.println("3. LISTAR");
            System.out.println("4. EDITAR");
            System.out.println("5. NUEVO");
            System.out.println("6. VOLVER");

            int choice = Main.getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    handleBuyDrinkable();
                    break;
                case 2:
                    handleSellDrinkable();
                    break;
                case 3:
                    handleListDrinkable();
                    break;
                case 4:
                    handleEditDrinkable();
                    break;
                case 5:
                    handleNewDrinkable();
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void handleBuyDrinkable() {
    System.out.println("Comprar producto bebible.");

    List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
    if (drinkableProducts.isEmpty()) {
        System.out.println("No hay productos bebibles disponibles.");
        return;
    }

    System.out.println("Productos disponibles:");
    for (Drinkable drinkable : drinkableProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f%n",
                drinkable.getId(), drinkable.getDescription(), drinkable.getPrice());
    }

    String id = Handler.getStringInput("Código del producto (ACxxx): ");
    Drinkable drinkable = findDrinkableById(id);

    if (drinkable == null) {
        System.out.println("No existe ese código de producto.");
        return;
    }

    System.out.printf("Detalles del producto seleccionado:%n");
    System.out.printf("Código: %s%n", drinkable.getId());
    System.out.printf("Descripción: %s%n", drinkable.getDescription());
    System.out.printf("Precio: %.2f%n", drinkable.getPrice());
    System.out.printf("Stock disponible: %d%n", drinkable.getStock());

    int quantity = Handler.getIntInput("Cantidad a comprar: ");
    float totalPrice = drinkable.getPrice() * quantity;

    if (quantity <= 0 || totalPrice <= 0) {
        System.out.println("Cantidad o precio no válidos.");
        return;
    }

    if (quantity > drinkable.getStock()) {
        System.out.println("Cantidad solicitada excede el stock disponible.");
        return;
    }

    if (totalPrice > Balance) {
        System.out.println("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");
        return;
    }

    drinkable.setStock(drinkable.getStock() - quantity);
    Balance -= totalPrice;
    fileDrinkable.editDrinkable(id, drinkable);

    System.out.println("Compra realizada con éxito. Saldo restante: " + Balance);
}

    private static void handleSellDrinkable() {
    System.out.println("Vender productos bebibles.");

    List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
    if (drinkableProducts.isEmpty()) {
        System.out.println("No hay productos bebibles disponibles.");
        return;
    }

    System.out.println("Productos disponibles:");
    for (Drinkable drinkable : drinkableProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d%n",
                drinkable.getId(), drinkable.getDescription(), drinkable.getPrice(), drinkable.getStock());
    }

    String id = Handler.getStringInput("Código del producto (ACxxx): ");
    Drinkable drinkable = findDrinkableById(id);

    if (drinkable == null) {
        System.out.println("No existe ese código de producto.");
        return;
    }

    System.out.printf("Detalles del producto seleccionado:%n");
    System.out.printf("Código: %s%n", drinkable.getId());
    System.out.printf("Descripción: %s%n", drinkable.getDescription());
    System.out.printf("Precio: %.2f%n", drinkable.getPrice());
    System.out.printf("Stock disponible: %d%n", drinkable.getStock());

    int quantity = Handler.getIntInput("Cantidad a vender: ");
    float totalPrice = drinkable.getPrice() * quantity;

    if (quantity <= 0 || totalPrice <= 0) {
        System.out.println("Cantidad o precio no válidos.");
        return;
    }

    if (quantity > drinkable.getStock()) {
        System.out.println("Cantidad solicitada excede el stock disponible.");
        return;
    }

    drinkable.setStock(drinkable.getStock() - quantity);
    Balance += totalPrice;
    fileDrinkable.editDrinkable(id, drinkable);

    System.out.println("Venta realizada con éxito. Saldo actualizado: " + Balance);
}

    private static void handleListDrinkable() {
    List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
    if (drinkableProducts.isEmpty()) {
        System.out.println("No hay productos bebibles disponibles.");
        return;
    }

    System.out.println("Lista de productos bebibles:");
    for (Drinkable drinkable : drinkableProducts) {
        System.out.println(drinkable);
    }
}

    private static void handleEditDrinkable() {
        System.out.println("Editar productos bebibles.");

        String idToEdit = Handler.getStringInput("Ingresa el código del producto a editar (e.g., AB001): ");

        List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();

        Drinkable productToEdit = null;
        for (Drinkable drinkable : drinkableProducts) {
            if (drinkable.getId().equals(idToEdit)) {
                productToEdit = drinkable;
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
        boolean imported = Handler.getIntInput("Importado (1 para sí, 0 para no) (" + (productToEdit.isImported() ? "1" : "0") + "): ") == 1;
        LocalDate expirationDate = Handler.getLocalDateInput("Fecha de vencimiento (yyyy-MM-dd) (" + productToEdit.getExpirationDate() + "): ");
        int calories = Handler.getIntInput("Calorías (" + productToEdit.getCalories() + "): ");
        float discount = Handler.getFloatInput("Descuento (%) (" + productToEdit.getDiscountPercentage() + "): ");
        Drinkable updatedDrinkable = new Drinkable(
                description.isEmpty() ? productToEdit.getDescription() : description,
                stock,
                price,
                percentage,
                available,
                productToEdit.getAlcoholicGraduation(),
                imported,
                expirationDate,
                calories,
                discount
        );

        for (int i = 0; i < drinkableProducts.size(); i++) {
            if (drinkableProducts.get(i).getId().equals(idToEdit)) {
                drinkableProducts.set(i, updatedDrinkable);
                break;
            }
        }
        fileDrinkable.saveDrinkableToFile(drinkableProducts);

        System.out.println("Producto bebible actualizado exitosamente.");
    }

    private static void handleNewDrinkable() {
        System.out.println("Nuevo producto bebible.");

        String description = Handler.getStringInput("Descripción: ");
        int stock = Handler.getIntInput("Stock: ");
        float price = Handler.getFloatInput("Precio: ");
        float percentage = Handler.getFloatInput("Porcentaje: ");
        boolean available = Handler.getIntInput("Disponible (1 para sí, 0 para no): ") == 1;
        float alcoholicGraduation = Handler.getFloatInput("Graduación alcohólica: ");
        boolean imported = Handler.getIntInput("Importado (1 para sí, 0 para no): ") == 1;
        LocalDate expirationDate = Handler.getLocalDateInput("Fecha de vencimiento (yyyy-MM-dd): ");
        int calories = Handler.getIntInput("Calorías: ");
        float discount = Handler.getFloatInput("Descuento (%): ");

        Drinkable newDrinkable = new Drinkable(
                description,
                stock,
                price,
                percentage,
                available,
                alcoholicGraduation,
                imported,
                expirationDate,
                calories,
                discount
        );

        List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
        int currentStockTotal = drinkableProducts.stream()
                .mapToInt(Drinkable::getStock)
                .sum();

        if (currentStockTotal + stock > tienda.getStockLimit()) {
            System.out.println("No se puede agregar el producto. El stock total excede el límite permitido de la tienda.");
            return;
        }

        fileDrinkable.addDrinkable(newDrinkable);

        System.out.println("Producto bebible creado y añadido exitosamente.");
    }

    public static void mostrarMenúLimpieza() {
        while (true) {
            System.out.println("Menu LIMPIEZA:");
            System.out.println("1. COMPRAR");
            System.out.println("2. VENDER");
            System.out.println("3. LISTAR");
            System.out.println("4. EDITAR");
            System.out.println("5. NUEVO");
            System.out.println("6. VOLVER");

            int choice = Main.getIntInput("Elige una opción: ");

            switch (choice) {
                case 1:
                    handleBuyCleaner();
                    break;
                case 2:
                    handleSellCleaner();
                    break;
                case 3:
                    handleListCleaner();
                    break;
                case 4:
                    handleEditCleaner();
                    break;
                case 5:
                    handleNewCleaner();
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void handleBuyCleaner() {
    System.out.println("Comprar producto de limpieza.");

    List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();
    if (cleanerProducts.isEmpty()) {
        System.out.println("No hay productos de limpieza disponibles.");
        return;
    }

    System.out.println("Productos disponibles:");
    for (Cleaner cleaner : cleanerProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f%n",
                cleaner.getId(), cleaner.getDescription(), cleaner.getPrice());
    }

    String id = Handler.getStringInput("Código del producto (CLxxx): ");
    Cleaner cleaner = findCleanerById(id);

    if (cleaner == null) {
        System.out.println("No existe ese código de producto.");
        return;
    }

    System.out.printf("Detalles del producto seleccionado:%n");
    System.out.printf("Código: %s%n", cleaner.getId());
    System.out.printf("Descripción: %s%n", cleaner.getDescription());
    System.out.printf("Precio: %.2f%n", cleaner.getPrice());
    System.out.printf("Stock disponible: %d%n", cleaner.getStock());

    int quantity = Handler.getIntInput("Cantidad a comprar: ");
    float totalPrice = cleaner.getPrice() * quantity;

    if (quantity <= 0 || totalPrice <= 0) {
        System.out.println("Cantidad o precio no válidos.");
        return;
    }

    if (quantity > cleaner.getStock()) {
        System.out.println("Cantidad solicitada excede el stock disponible.");
        return;
    }

    if (totalPrice > Balance) {
        System.out.println("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");
        return;
    }

    cleaner.setStock(cleaner.getStock() - quantity);
    Balance -= totalPrice;
    fileCleaner.editCleaner(id, cleaner);

    System.out.println("Compra realizada con éxito. Saldo restante: " + Balance);
}

    private static void handleSellCleaner() {
    System.out.println("Vender productos de limpieza.");

    List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();
    if (cleanerProducts.isEmpty()) {
        System.out.println("No hay productos de limpieza disponibles.");
        return;
    }

    System.out.println("Productos disponibles:");
    for (Cleaner cleaner : cleanerProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d%n",
                cleaner.getId(), cleaner.getDescription(), cleaner.getPrice(), cleaner.getStock());
    }

    String id = Handler.getStringInput("Código del producto (CLxxx): ");
    Cleaner cleaner = findCleanerById(id);

    if (cleaner == null) {
        System.out.println("No existe ese código de producto.");
        return;
    }

    System.out.printf("Detalles del producto seleccionado:%n");
    System.out.printf("Código: %s%n", cleaner.getId());
    System.out.printf("Descripción: %s%n", cleaner.getDescription());
    System.out.printf("Precio: %.2f%n", cleaner.getPrice());
    System.out.printf("Stock disponible: %d%n", cleaner.getStock());

    int quantity = Handler.getIntInput("Cantidad a vender: ");
    float totalPrice = cleaner.getPrice() * quantity;

    if (quantity <= 0 || totalPrice <= 0) {
        System.out.println("Cantidad o precio no válidos.");
        return;
    }

    if (quantity > cleaner.getStock()) {
        System.out.println("Cantidad solicitada excede el stock disponible.");
        return;
    }

    cleaner.setStock(cleaner.getStock() - quantity);
    Balance += totalPrice;
    fileCleaner.editCleaner(id, cleaner);

    System.out.println("Venta realizada con éxito. Saldo actualizado: " + Balance);
}

    private static void handleListCleaner() {
    List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();
    if (cleanerProducts.isEmpty()) {
        System.out.println("No hay productos de limpieza disponibles.");
        return;
    }

    System.out.println("Lista de productos de limpieza:");
    for (Cleaner cleaner : cleanerProducts) {
        System.out.println(cleaner);
    }
}

    private static void handleEditCleaner() {
        System.out.println("Editar productos de limpieza.");

        String idToEdit = Handler.getStringInput("Ingresa el código del producto a editar (e.g., CL001): ");

        List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();

        Cleaner productToEdit = null;
        for (Cleaner cleaner : cleanerProducts) {
            if (cleaner.getId().equals(idToEdit)) {
                productToEdit = cleaner;
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
        Cleaner.ApplicationType applicationType = Handler.getApplicationTypeInput("Tipo de aplicación (COCINA, BAÑO, ROPA, MULTIUSO) (" + productToEdit.getApplicationType() + "): ");
        float discount = Handler.getFloatInput("Descuento (%) (" + productToEdit.getDiscountPercentage() + "): ");
        Cleaner updatedCleaner = new Cleaner(
                productToEdit.getId(),
                description.isEmpty() ? productToEdit.getDescription() : description,
                stock,
                price,
                percentage,
                available,
                applicationType,
                discount
        );

        for (int i = 0; i < cleanerProducts.size(); i++) {
            if (cleanerProducts.get(i).getId().equals(idToEdit)) {
                cleanerProducts.set(i, updatedCleaner);
                break;
            }
        }
        fileCleaner.saveCleanerToFile(cleanerProducts);

        System.out.println("Producto de limpieza actualizado exitosamente.");
    }

    private static void handleNewCleaner() {
        System.out.println("Nuevo producto de limpieza.");

        String description = Handler.getStringInput("Descripción: ");
        int stock = Handler.getIntInput("Stock: ");
        float price = Handler.getFloatInput("Precio: ");
        float percentage = Handler.getFloatInput("Porcentaje: ");
        boolean available = Handler.getIntInput("Disponible (1 para sí, 0 para no): ") == 1;
        Cleaner.ApplicationType applicationType = Handler.getApplicationTypeInput("Tipo de aplicación (COCINA, BAÑO, ROPA, MULTIUSO): ");
        float discount = Handler.getFloatInput("Descuento (%): ");

        Cleaner newCleaner = new Cleaner(
                Cleaner.generateId(),
                description,
                stock,
                price,
                percentage,
                available,
                applicationType,
                discount
        );

        List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();
        int currentStockTotal = cleanerProducts.stream()
                .mapToInt(Cleaner::getStock)
                .sum();

        if (currentStockTotal + stock > tienda.getStockLimit()) {
            System.out.println("No se puede agregar el producto. El stock total excede el límite permitido de la tienda.");
            return;
        }

        fileCleaner.addCleaner(newCleaner);

        System.out.println("Producto de limpieza creado y añadido exitosamente.");
    }

    private static Cleaner findCleanerById(String id) {
    List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();
    for (Cleaner cleaner : cleanerProducts) {
        if (cleaner.getId().equals(id)) {
            return cleaner;
        }
    }
    return null;
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

    static void generalSell() {
    System.out.println("Venta general de productos.");

    List<Packaged> packagedProducts = filePackaged.readAllPackaged();
    List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
    List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();

    System.out.println("Productos empaquetados disponibles:");
    for (Packaged packaged : packagedProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d%n",
                packaged.getId(), packaged.getDescription(), packaged.getPrice(), packaged.getStock());
    }

    System.out.println("Productos bebibles disponibles:");
    for (Drinkable drinkable : drinkableProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d%n",
                drinkable.getId(), drinkable.getDescription(), drinkable.getPrice(), drinkable.getStock());
    }

    System.out.println("Productos de limpieza disponibles:");
    for (Cleaner cleaner : cleanerProducts) {
        System.out.printf("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d%n",
                cleaner.getId(), cleaner.getDescription(), cleaner.getPrice(), cleaner.getStock());
    }

    float totalVenta = 0;
    StringBuilder detalleVenta = new StringBuilder();
    boolean stockInsuficiente = false;
    boolean productoNoDisponible = false;

    for (int i = 0; i < 3; i++) {
        String id = Handler.getStringInput("Código del producto a vender (o 'salir' para terminar): ");
        if (id.equalsIgnoreCase("salir")) {
            break;
        }

        Product product = findProductById(id);
        if (product == null) {
            System.out.println("No existe ese código de producto.");
            continue;
        }

        if (!product.isAvailable()) {
            System.out.printf("El producto %s %s no se encuentra disponible.%n", product.getId(), product.getDescription());
            detalleVenta.append(String.format("El producto %s %s no se encuentra disponible.%n", product.getId(), product.getDescription()));
            productoNoDisponible = true;
            continue;
        }

        System.out.printf("Detalles del producto seleccionado:%n");
        System.out.printf("Código: %s%n", product.getId());
        System.out.printf("Descripción: %s%n", product.getDescription());
        System.out.printf("Precio: %.2f%n", product.getPrice());
        System.out.printf("Stock disponible: %d%n", product.getStock());

        int quantity = Handler.getIntInput("Cantidad a vender (máximo 12): ");
        if (quantity <= 0 || quantity > 12) {
            System.out.println("Cantidad no válida.");
            continue;
        }

        if (quantity > product.getStock()) {
            System.out.println("Cantidad solicitada excede el stock disponible. Se venderá solo el stock disponible.");
            quantity = product.getStock();
            product.setAvailable(false);
            stockInsuficiente = true;
        }

        float totalPrice = product.getPrice() * quantity;
        product.setStock(product.getStock() - quantity);
        Balance += totalPrice;
        totalVenta += totalPrice;

        detalleVenta.append(String.format("%s %s %d x %.2f%n", product.getId(), product.getDescription(), quantity, product.getPrice()));

        if (product instanceof Packaged) {
            filePackaged.editPackaged(product.getId(), (Packaged) product);
        } else if (product instanceof Drinkable) {
            fileDrinkable.editDrinkable(product.getId(), (Drinkable) product);
        } else if (product instanceof Cleaner) {
            fileCleaner.editCleaner(product.getId(), (Cleaner) product);
        }

        System.out.println("Venta realizada con éxito. Saldo actualizado: " + Balance);
    }

    System.out.println("Detalle de la venta:");
    System.out.print(detalleVenta.toString());
    if (stockInsuficiente) {
        System.out.println("Hay productos con stock disponible menor al solicitado.");
    }
    if (productoNoDisponible) {
        System.out.println("Algunos productos no estaban disponibles para la venta.");
    }
    System.out.printf("TOTAL VENTA: %.2f%n", totalVenta);

    Main.main(null);
}

private static Product findProductById(String id) {
    List<Packaged> packagedProducts = filePackaged.readAllPackaged();
    for (Packaged packaged : packagedProducts) {
        if (packaged.getId().equals(id)) {
            return packaged;
        }
    }

    List<Drinkable> drinkableProducts = fileDrinkable.readAllDrinkable();
    for (Drinkable drinkable : drinkableProducts) {
        if (drinkable.getId().equals(id)) {
            return drinkable;
        }
    }

    List<Cleaner> cleanerProducts = fileCleaner.readAllCleaner();
    for (Cleaner cleaner : cleanerProducts) {
        if (cleaner.getId().equals(id)) {
            return cleaner;
        }
    }

    return null;
}
}
