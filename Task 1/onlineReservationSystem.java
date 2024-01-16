import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class onlineReservationSystem {

    private static final Map<String, String> userDatabase = new HashMap<>();
    private static final Map<Integer, Reservation> reservations = new HashMap<>();
    private static int pnrCounter = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        userDatabase.put("user1", "password1");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Exiting Online Reservation System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (validateUser(username, password)) {
            System.out.println("Login successful!");

            while (true) {
                System.out.println("\n1. Reservation System");
                System.out.println("2. Cancellation System");
                System.out.println("3. View Reservations");
                System.out.println("4. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        makeReservation(username);
                        break;
                    case 2:
                        cancelReservation(username);
                        break;
                    case 3:
                        viewReservations(username);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }
    }

    private static boolean validateUser(String username, String password) {
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }

    private static void makeReservation(String username) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter train number: ");
        String trainNumber = scanner.next();

        System.out.println("Enter class type: ");
        String classType = scanner.next();

        System.out.println("Enter date of journey: ");
        String dateOfJourney = scanner.next();

        int pnr = ++pnrCounter;
        Reservation reservation = new Reservation(pnr, username, trainNumber, classType, dateOfJourney);
        reservations.put(pnr, reservation);

        System.out.println("Reservation successful!");
        System.out.println("PNR: " + pnr);
    }

    private static void cancelReservation(String username) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter PNR to cancel: ");
        int pnrToCancel = scanner.nextInt();

        if (reservations.containsKey(pnrToCancel) && reservations.get(pnrToCancel).getUsername().equals(username)) {
            Reservation canceledReservation = reservations.remove(pnrToCancel);
            System.out.println("Reservation canceled successfully for PNR: " + pnrToCancel);
        } else {
            System.out.println("Invalid PNR or not authorized to cancel this reservation.");
        }
    }

    private static void viewReservations(String username) {
        System.out.println("Your Reservations:");
        reservations.values().stream()
                .filter(reservation -> reservation.getUsername().equals(username))
                .forEach(reservation -> {
                    System.out.println("PNR: " + reservation.getPnr());
                    System.out.println("Train Number: " + reservation.getTrainNumber());
                    System.out.println("Class Type: " + reservation.getClassType());
                    System.out.println("Date of Journey: " + reservation.getDateOfJourney());
                    System.out.println("------------------------------");
                });
    }
}

class Reservation {
    private int pnr;
    private String username;
    private String trainNumber;
    private String classType;
    private String dateOfJourney;

    public Reservation(int pnr, String username, String trainNumber, String classType, String dateOfJourney) {
        this.pnr = pnr;
        this.username = username;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
    }

    public int getPnr() {
        return pnr;
    }

    public String getUsername() {
        return username;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getClassType() {
        return classType;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }
}
