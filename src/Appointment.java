import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Appointment {

    public void bookAppointment(Scanner input) {

        String patientName;
        String selectedService = "";
        String appointmentTime = "";
        String selectedStaff = "";
        int serviceFee = 0;

        System.out.println("\n======================================");
        System.out.println("          BOOK AN APPOINTMENT");
        System.out.println("======================================");

        System.out.print("Enter client name: ");
        patientName = input.nextLine();

        while (patientName.trim().isEmpty()) {
            System.out.println("Client name cannot be empty.");
            System.out.print("Enter client name: ");
            patientName = input.nextLine();
        }

        // Select service
        new Service().services();

        int serviceChoice;

        while (true) {
            System.out.print("Select service: ");

            if (input.hasNextInt()) {
                serviceChoice = input.nextInt();
                input.nextLine();

                if (serviceChoice >= 1 && serviceChoice <= Service.NAMES.length) {
                    break;
                } else {
                    System.out.println("Invalid choice! Please select 1-"+ Service.NAMES.length);
                }

            } else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }
            selectedService = Service.NAMES[serviceChoice - 1];
            serviceFee = Service.FEES[serviceChoice - 1];
        // Select date
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MM/dd/uuuu")
                        .withResolverStyle(ResolverStyle.STRICT);

        LocalDate today = LocalDate.now();
        LocalDate date;

        while (true) {

            System.out.println("\n===== DATE =====");
            System.out.print("Enter appointment date (MM/DD/YYYY): ");

            String appointmentDate = input.nextLine();

            try {
                date = LocalDate.parse(appointmentDate, dateFormat);

                if (date.isBefore(today)) {
                    System.out.println("Invalid date! Please choose today or a future date.");
                } else {
                    break;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use MM/DD/YYYY.");
            }
        }

        // Select time
        System.out.println("\n===== SELECT TIME SLOT =====");
        System.out.println("[1] 8:00 AM  - 10:00 AM");
        System.out.println("[2] 10:00 AM - 12:00 PM");
        System.out.println("[3] 1:00 PM  - 3:00 PM");
        System.out.println("[4] 3:00 PM  - 5:00 PM");

        int timeChoice;

        while (true) {

            System.out.print("Select time slot: ");

            if (input.hasNextInt()) {
                timeChoice = input.nextInt();
                input.nextLine();

                if (timeChoice >= 1 && timeChoice <= 4) {

                    if (timeChoice == 1) {
                        appointmentTime = "8:00 AM - 10:00 AM";

                    } else if (timeChoice == 2) {
                        appointmentTime = "10:00 AM - 12:00 PM";

                    } else if (timeChoice == 3) {
                        appointmentTime = "1:00 PM - 3:00 PM";

                    } else {
                        appointmentTime = "3:00 PM - 5:00 PM";
                    }

                    break;

                } else {
                    System.out.println("Invalid choice! Please select 1-4.");
                }

            } else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }

        // Select staff
        System.out.println("\n===== SELECT STAFF =====");

        for (int i = 0; i < Staff.staffList.length; i++) {
            System.out.println("[" + (i + 1) + "] "
                    + Staff.staffList[i].name);
        }

        int staffChoice;

        while (true) {

            System.out.print("Select staff: ");

            if (input.hasNextInt()) {
                staffChoice = input.nextInt();
                input.nextLine();

                if (staffChoice >= 1
                        && staffChoice <= Staff.staffList.length) {

                    selectedStaff =
                            Staff.staffList[staffChoice - 1].name;

                    break;

                } else {
                    System.out.println("Invalid choice! Please select a valid staff.");
                }

            } else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }

        // Confirm appointment
        System.out.println("\n===== APPOINTMENT SUMMARY =====");
        System.out.println("Patient Name : " + patientName);
        System.out.println("Service      : " + selectedService);
        System.out.println("Date         : " + date.format(dateFormat));
        System.out.println("Time         : " + appointmentTime);
        System.out.println("Staff        : " + selectedStaff);
        System.out.println("Estimated Fee: PHP " + serviceFee);

        String confirmation;

        while (true) {
            System.out.print("\nConfirm Appointment? (Y/N): ");
            confirmation = input.nextLine();

            if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("N")) {
                break;
            }

            System.out.println("Invalid input! Please enter Y or N.");
        }

        if (confirmation.equalsIgnoreCase("Y")) {

            AppointmentRecord newAppointment =
                    new AppointmentRecord(
                            patientName,
                            selectedService,
                            date,
                            appointmentTime,
                            selectedStaff,
                            serviceFee
                    );

            if (AppointmentRecord.addAppointment(newAppointment)) {

                System.out.println("\nAppointment Confirmed!");

                System.out.println("\n======================================");
                System.out.println("       APPOINTMENT CONFIRMED");
                System.out.println("======================================");
                System.out.println("Patient Name : " + patientName);
                System.out.println("Service      : " + selectedService);
                System.out.println("Date         : " + date.format(dateFormat));
                System.out.println("Time         : " + appointmentTime);
                System.out.println("Staff        : " + selectedStaff);
                System.out.println("Estimated Fee: PHP " + serviceFee);
                System.out.println("Status       : Confirmed");
                System.out.println("======================================");

            } else {
                System.out.println("This schedule is already booked.");
            }

        } else {

            System.out.println("Appointment not confirmed.");
        }
    }
}
