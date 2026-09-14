
import java.util.Scanner;

public class DentalCare {

    public void showMenu(Scanner input) {

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("       DENTALCARE APPOINTMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("[1] View Dental Services");
            System.out.println("[2] Book an Appointment");
            System.out.println("[3] View All Appointments");
            System.out.println("[4] Cancel an Appointment");
            System.out.println("[5] Exit");
            System.out.print("Enter your choice: ");

            if (input.hasNextInt()) {

                choice = input.nextInt();
                input.nextLine();

                switch (choice) {

                    case 1:
                        new Service().services();
                        break;

                    case 2:
                        new Appointment().bookAppointment(input);
                        break;

                    case 3:
                        AppointmentRecord.viewAppointments();
                        break;

                    case 4:
                        AppointmentRecord.cancelAppointment(input);
                        break;

                    case 5:
                        System.out.println("\nThank you for using DentalCare!");
                        break;

                    default:
                        System.out.println("Invalid choice! Please select 1-5.");
                }

            } else {

                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
                choice = 0;
            }

        } while (choice != 5);
    }
}