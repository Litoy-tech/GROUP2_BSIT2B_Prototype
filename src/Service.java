public class Service {
    public static final String[] NAMES = {
            "Dental Check-up", "Teeth Cleaning", "Tooth Extraction", "Dental Filling"
    };
    public static final int[] FEES = {300, 500, 800, 1000};

    public void services() {
        System.out.println("\n======================================");
        System.out.println("          DENTAL SERVICES");
        System.out.println("======================================");
        for (int i = 0; i < NAMES.length; i++) {
            System.out.printf("[%d] %-20s - PHP %d%n", i + 1, NAMES[i], FEES[i]);
        }
        System.out.println("======================================");
    }
}