import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {

    private List<Room> rooms;
    private List<Reservation> reservations;

    private int nextBookingId = 1001;

    public Hotel() {

        rooms = FileManager.loadRooms();

        if (rooms.isEmpty()) {
            createDefaultRooms();
        }

        reservations =
                FileManager.loadReservations(rooms);

        updateNextBookingId();
    }

    private void createDefaultRooms() {

        rooms.add(
                new Room(101, "Standard", 1500)
        );

        rooms.add(
                new Room(102, "Standard", 1500)
        );

        rooms.add(
                new Room(103, "Standard", 1500)
        );

        rooms.add(
                new Room(201, "Deluxe", 2500)
        );

        rooms.add(
                new Room(202, "Deluxe", 2500)
        );

        rooms.add(
                new Room(203, "Deluxe", 2500)
        );

        rooms.add(
                new Room(301, "Suite", 4500)
        );

        rooms.add(
                new Room(302, "Suite", 4500)
        );

        FileManager.saveRooms(rooms);
    }

    private void updateNextBookingId() {

        for (Reservation reservation : reservations) {

            if (reservation.getBookingId() >= nextBookingId) {

                nextBookingId =
                        reservation.getBookingId() + 1;
            }
        }
    }

    public void searchRooms(Scanner scanner) {

        System.out.println("\n========== SEARCH ROOMS ==========");

        System.out.println("1. All Categories");
        System.out.println("2. Standard");
        System.out.println("3. Deluxe");
        System.out.println("4. Suite");

        System.out.print("Enter category: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String category = "";

        switch (choice) {

            case 1:
                category = "All";
                break;

            case 2:
                category = "Standard";
                break;

            case 3:
                category = "Deluxe";
                break;

            case 4:
                category = "Suite";
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }

        boolean found = false;

        System.out.println("\nAvailable Rooms");
        System.out.println("--------------------------------------");

        for (Room room : rooms) {

            if (!room.isAvailable()) {
                continue;
            }

            if (category.equals("All") ||
                    room.getCategory().equalsIgnoreCase(category)) {

                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No rooms available in this category."
            );
        }
    }

    public void bookRoom(Scanner scanner) {

        System.out.println("\n========== BOOK ROOM ==========");

        searchRooms(scanner);

        System.out.print(
                "\nEnter room number to book: "
        );

        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {

            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.isAvailable()) {

            System.out.println(
                    "Sorry, this room is already booked."
            );

            return;
        }

        System.out.println("\n========== CUSTOMER DETAILS ==========");

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter check-in date: ");
        String checkIn = scanner.nextLine();

        System.out.print("Enter check-out date: ");
        String checkOut = scanner.nextLine();

        System.out.print(
                "Enter number of nights: "
        );

        int nights = scanner.nextInt();
        scanner.nextLine();

        if (nights <= 0) {

            System.out.println(
                    "Number of nights must be greater than 0."
            );

            return;
        }

        double totalAmount =
                selectedRoom.getPrice() * nights;

        System.out.println("\n========== BOOKING SUMMARY ==========");

        System.out.println(
                "Room Number : "
                        + selectedRoom.getRoomNumber()
        );

        System.out.println(
                "Category    : "
                        + selectedRoom.getCategory()
        );

        System.out.println(
                "Price/Night : ₹"
                        + selectedRoom.getPrice()
        );

        System.out.println(
                "Nights      : "
                        + nights
        );

        System.out.println(
                "Total       : ₹"
                        + totalAmount
        );

        System.out.print(
                "\nProceed with payment? (Y/N): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Booking cancelled."
            );

            return;
        }

        boolean paymentSuccessful =
                Payment.processPayment(
                        totalAmount,
                        scanner
                );

        if (!paymentSuccessful) {

            System.out.println(
                    "Booking failed because payment was unsuccessful."
            );

            return;
        }

        Customer customer =
                new Customer(
                        name,
                        phone,
                        email
                );

        Reservation reservation =
                new Reservation(
                        nextBookingId++,
                        customer,
                        selectedRoom,
                        checkIn,
                        checkOut,
                        totalAmount,
                        "PAID",
                        "CONFIRMED"
                );

        reservations.add(reservation);

        selectedRoom.setAvailable(false);

        saveData();

        System.out.println(
                "\nBooking successful!"
        );

        System.out.println(
                "Your Booking ID: "
                        + reservation.getBookingId()
        );
    }

    public void viewBooking(Scanner scanner) {

        System.out.println(
                "\n========== VIEW BOOKING =========="
        );

        System.out.print(
                "Enter booking ID: "
        );

        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Reservation reservation =
                findReservation(bookingId);

        if (reservation == null) {

            System.out.println(
                    "Booking not found."
            );

            return;
        }

        reservation.displayDetails();
    }

    public void cancelBooking(Scanner scanner) {

        System.out.println(
                "\n========== CANCEL BOOKING =========="
        );

        System.out.print(
                "Enter booking ID: "
        );

        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Reservation reservation =
                findReservation(bookingId);

        if (reservation == null) {

            System.out.println(
                    "Booking not found."
            );

            return;
        }

        if (reservation
                .getBookingStatus()
                .equals("CANCELLED")) {

            System.out.println(
                    "This booking is already cancelled."
            );

            return;
        }

        reservation.setBookingStatus(
                "CANCELLED"
        );

        reservation
                .getRoom()
                .setAvailable(true);

        saveData();

        System.out.println(
                "Booking cancelled successfully."
        );

        System.out.println(
                "Room "
                        + reservation.getRoom().getRoomNumber()
                        + " is now available."
        );
    }

    public void showAllRooms() {

        System.out.println(
                "\n========== ALL ROOMS =========="
        );

        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void showAllBookings() {

        System.out.println(
                "\n========== ALL BOOKINGS =========="
        );

        if (reservations.isEmpty()) {

            System.out.println(
                    "No bookings found."
            );

            return;
        }

        for (Reservation reservation : reservations) {

            System.out.println(
                    "\nBooking ID: "
                            + reservation.getBookingId()
            );

            System.out.println(
                    "Customer: "
                            + reservation
                            .getCustomer()
                            .getName()
            );

            System.out.println(
                    "Room: "
                            + reservation
                            .getRoom()
                            .getRoomNumber()
            );

            System.out.println(
                    "Category: "
                            + reservation
                            .getRoom()
                            .getCategory()
            );

            System.out.println(
                    "Amount: ₹"
                            + reservation.getTotalAmount()
            );

            System.out.println(
                    "Status: "
                            + reservation.getBookingStatus()
            );

            System.out.println(
                    "----------------------------------"
            );
        }
    }

    private Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }

    private Reservation findReservation(
            int bookingId) {

        for (Reservation reservation : reservations) {

            if (reservation.getBookingId() == bookingId) {
                return reservation;
            }
        }

        return null;
    }

    private void saveData() {

        FileManager.saveRooms(rooms);

        FileManager.saveReservations(
                reservations
        );
    }
}