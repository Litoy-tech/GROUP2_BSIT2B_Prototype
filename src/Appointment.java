import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Appointment {

    public void bookAppointment(Scanner input) {

        String patientName, patientAddress, patientContact;
        String selectedService = "";
        String appointmentTime = "";
        String selectedStaff = "";
        int serviceFee = 0;

        System.out.println("\n======================================");
        System.out.println("          BOOK AN APPOINTMENT");
        System.out.println("======================================");

        // Step 1: Select Dentist
        System.out.println("\n===== SELECT DENTIST =====");
        for (int i = 0; i < Staff.staffList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + Staff.staffList[i].name);
        }

        int staffChoice;
        while (true) {
            System.out.print("Select dentist: ");
            if (input.hasNextInt()) {
                staffChoice = input.nextInt();
                input.nextLine();
                if (staffChoice >= 1 && staffChoice <= Staff.staffList.length) {
                    selectedStaff = Staff.staffList[staffChoice - 1].name;
                    break;
                } else {
                    System.out.println("Invalid choice! Please select a valid dentist.");
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }

        // Step 2 & 3: Select Available Date + Time Slot (loop together, check dentist availability)
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate today = LocalDate.now();
        LocalDate date;
        int timeChoice;

        while (true) {

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

            System.out.println("\n===== SELECT TIME SLOT =====");
            System.out.println("[1] 8:00 AM  - 10:00 AM");
            System.out.println("[2] 10:00 AM - 12:00 PM");
            System.out.println("[3] 1:00 PM  - 3:00 PM");
            System.out.println("[4] 3:00 PM  - 5:00 PM");

            while (true) {
                System.out.print("Select time slot: ");
                if (input.hasNextInt()) {
                    timeChoice = input.nextInt();
                    input.nextLine();
                    if (timeChoice >= 1 && timeChoice <= 4) {
                        if (timeChoice == 1) appointmentTime = "8:00 AM - 10:00 AM";
                        else if (timeChoice == 2) appointmentTime = "10:00 AM - 12:00 PM";
                        else if (timeChoice == 3) appointmentTime = "1:00 PM - 3:00 PM";
                        else appointmentTime = "3:00 PM - 5:00 PM";
                        break;
                    } else {
                        System.out.println("Invalid choice! Please select 1-4.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }

            if (AppointmentRecord.hasConflict(null, date, appointmentTime, selectedStaff)) {
                System.out.println("\n" + selectedStaff + " is already booked at this date and time.");
                System.out.println("Please choose a different date or time slot.");
            } else {
                break;
            }
        }

        // Step 4: Enter Patient Information
        System.out.println("\n===== PATIENT INFORMATION =====");

        System.out.print("Enter client name: ");
        patientName = input.nextLine();
        while (patientName.trim().isEmpty()) {
            System.out.println("Client name cannot be empty.");
            System.out.print("Enter client name: ");
            patientName = input.nextLine();
        }

        System.out.print("Enter client address: ");
        patientAddress = input.nextLine();
        while (patientAddress.trim().isEmpty()) {
            System.out.println("Client address cannot be empty.");
            System.out.print("Enter client address: ");
            patientAddress = input.nextLine();
        }

        System.out.print("Enter client contact #: ");
        patientContact = input.nextLine();
        while (patientContact.trim().isEmpty()) {
            System.out.println("Client contact # cannot be empty.");
            System.out.print("Enter client contact #: ");
            patientContact = input.nextLine();
        }

        // Select service (needed for the fee — not shown in your diagram but required for the summary)
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
                    System.out.println("Invalid choice! Please select 1-" + Service.NAMES.length);
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }
        selectedService = Service.NAMES[serviceChoice - 1];
        serviceFee = Service.FEES[serviceChoice - 1];

        // Step 5: Confirm
        System.out.println("\n===== APPOINTMENT SUMMARY =====");
        System.out.println("Patient Name : " + patientName);
        System.out.println("Address      : " + patientAddress);
        System.out.println("Contact No.  : " + patientContact);
        System.out.println("Service      : " + selectedService);
        System.out.println("Date         : " + date.format(dateFormat));
        System.out.println("Time         : " + appointmentTime);
        System.out.println("Staff        : " + selectedStaff);
        System.out.println("Estimated Fee: PHP " + serviceFee);

        String confirmation;
        while (true) {
            System.out.print("\nConfirm Appointment? (Y/N): ");
            confirmation = input.nextLine();
            if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("N")) break;
            System.out.println("Invalid input! Please enter Y or N.");
        }

        // Step 6: Appointment Saved
        if (confirmation.equalsIgnoreCase("Y")) {

            AppointmentRecord newAppointment = new AppointmentRecord(
                    patientName, patientAddress, patientContact,
                    selectedService, date, appointmentTime, selectedStaff, serviceFee
            );

            if (AppointmentRecord.addAppointment(newAppointment)) {
                System.out.println("\nAppointment Confirmed!");
                System.out.println("\n======================================");
                System.out.println("       APPOINTMENT CONFIRMED");
                System.out.println("======================================");
                System.out.println("Patient Name : " + patientName);
                System.out.println("Address      : " + patientAddress);
                System.out.println("Contact No.  : " + patientContact);
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