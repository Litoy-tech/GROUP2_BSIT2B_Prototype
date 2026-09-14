import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String username = "staff";
        String password = "staff123";
        String enteredUsername = "";
        System.out.println("======================================");
        System.out.println("       DENTALCARE APPOINTMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("              STAFF LOGIN");
        System.out.println("======================================");

        int attempts = 3;
        boolean loggedIn = false;

        while (attempts > 0) {

            while(enteredUsername.isEmpty()){
                System.out.print("Username: ");
                enteredUsername = input.nextLine();

                if (enteredUsername.isEmpty()) {
                    System.out.println("Username cannot be empty!");
                }
            }
            System.out.print("Password: ");
            String enteredPassword = input.nextLine();


            if (enteredUsername.equals(username)
                    && enteredPassword.equals(password)) {

                loggedIn = true;
                System.out.print("\nLogin successful!...");
                break;

            } else {
                attempts--;
                System.out.print("Invalid username or password.");

                if (attempts > 0) {
                    System.out.println("Attempts remaining: " + attempts);
                }
            }
        }

        if (loggedIn) {
            input.nextLine();
            new DentalCare().showMenu(input);
        } else {
            System.out.println("\nToo many failed attempts.");
            System.out.println("Access denied.");
        }

        input.close();
    }
}