import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Hotel hotel = new Hotel();

        boolean running = true;

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "       HOTEL RESERVATION SYSTEM"
        );

        System.out.println(
                "=========================================="
        );

        while (running) {

            System.out.println("\n");
            System.out.println("========== MAIN MENU ==========");

            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Booking Details");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View All Rooms");
            System.out.println("6. View All Bookings");
            System.out.println("7. Exit");

            System.out.print("\nEnter your choice: ");

            int choice;

            try {

                choice = scanner.nextInt();
                scanner.nextLine();

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid number."
                );

                scanner.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    hotel.searchRooms(scanner);
                    break;

                case 2:
                    hotel.bookRoom(scanner);
                    break;

                case 3:
                    hotel.viewBooking(scanner);
                    break;

                case 4:
                    hotel.cancelBooking(scanner);
                    break;

                case 5:
                    hotel.showAllRooms();
                    break;

                case 6:
                    hotel.showAllBookings();
                    break;

                case 7:

                    running = false;

                    System.out.println(
                            "\nThank you for using "
                                    + "Hotel Reservation System!"
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }

        scanner.close();
    }
}