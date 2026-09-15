
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentRecord {

    static ArrayList<AppointmentRecord> appointments =
            new ArrayList<>();

    String patientName;
    String service;
    LocalDate date;
    String time;
    String staff;
    int fee;
    String status;

    public AppointmentRecord(String patientName, String service,
                             LocalDate date, String time,
                             String staff, int fee) {

        this.patientName = patientName;
        this.service = service;
        this.date = date;
        this.time = time;
        this.staff = staff;
        this.fee = fee;
        this.status = "Confirmed";
    }

    // Add a new appointment
    public static boolean addAppointment(AppointmentRecord newAppointment) {

        for (AppointmentRecord appointment : appointments) {

            if (appointment.status.equals("Confirmed")
                    && appointment.date.equals(newAppointment.date)
                    && appointment.time.equals(newAppointment.time)
                    && appointment.staff.equals(newAppointment.staff)) {

                return false;
            }
        }

        appointments.add(newAppointment);
        return true;
    }

    // View all appointments
    public static void viewAppointments() {

        System.out.println("\n======================================");
        System.out.println("          ALL APPOINTMENTS");
        System.out.println("======================================");

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (int i = 0; i < appointments.size(); i++) {

            AppointmentRecord a = appointments.get(i);

            System.out.println("\nAppointment #" + (i + 1));
            System.out.println("Patient : " + a.patientName);
            System.out.println("Service : " + a.service);
            System.out.println("Date    : " + a.date);
            System.out.println("Time    : " + a.time);
            System.out.println("Staff   : " + a.staff);
            System.out.println("Fee     : PHP " + a.fee);
            System.out.println("Status  : " + a.status);
        }
    }

    // Cancel an appointment
    public static void cancelAppointment(Scanner input) {

    if (appointments.isEmpty()) {
        System.out.println("\nNo appointments to cancel.");
        return;
    }

    viewAppointments();

    System.out.print("\nEnter appointment number to cancel: ");

    if (!input.hasNextInt()) {
        System.out.println("Invalid input! Please enter a number.");
        input.nextLine();
        return;
    }

    int choice = input.nextInt();
    input.nextLine();

    if (choice < 1 || choice > appointments.size()) {
        System.out.println("Invalid appointment number.");
        return;
    }

    AppointmentRecord selected = appointments.get(choice - 1);

    if (selected.status.equals("Cancelled")) {
        System.out.println("This appointment is already cancelled.");
        return;
    }

    String confirm;
    while (true) {
        System.out.print("Cancel appointment for " + selected.patientName
                + " on " + selected.date + "? (Y/N): ");
        confirm = input.nextLine();

        if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
            break;
        }
        System.out.println("Invalid input! Please enter Y or N.");
    }

    if (confirm.equalsIgnoreCase("Y")) {
        selected.status = "Cancelled";
        System.out.println("Appointment cancelled successfully!");
    } else {
        System.out.println("Cancellation aborted.");
    }
    }
}
