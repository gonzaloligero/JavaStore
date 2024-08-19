package model;


public class Cleaner extends Product {

    public enum ApplicationType {
        COCINA, BAÑO, ROPA, MULTIUSO
    }

    private ApplicationType applicationType;
    private static int instance = 0;

    public Cleaner(String description, int stock, float price, float porcentage, boolean available, ApplicationType applicationType, float discount) {
        super(generateId(), description, stock, price, porcentage, available, discount);
        this.applicationType = applicationType;
    }


    private static String generateId() {
        instance++;
        return String.format("AZ%03d", instance);
    }

    @Override
    protected boolean validateId(String id) {
        return id.matches("^AZ\\d{3}$");
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    @Override
    public String toString() {
        return "Cleaner{" +
                "applicationType=" + applicationType +
                "} " + super.toString();
    }
}
