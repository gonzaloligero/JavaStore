package model;

public class Screen {

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {new ProcessBuilder("clear").inheritIO().start().waitFor();}
        } catch (Exception e) {System.out.println("No se pudo limpiar la pantalla: " + e.getMessage());}
    }
}

