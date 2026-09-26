import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String ROOM_FILE = "rooms.txt";
    private static final String RESERVATION_FILE = "reservations.txt";

    public static void saveRooms(List<Room> rooms) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ROOM_FILE))) {

            for (Room room : rooms) {

                writer.write(
                        room.getRoomNumber() + "," +
                                room.getCategory() + "," +
                                room.getPrice() + "," +
                                room.isAvailable()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }
    }

    public static List<Room> loadRooms() {

        List<Room> rooms = new ArrayList<>();

        File file = new File(ROOM_FILE);

        if (!file.exists()) {
            return rooms;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {

                    int roomNumber = Integer.parseInt(data[0]);
                    String category = data[1];
                    double price = Double.parseDouble(data[2]);
                    boolean available = Boolean.parseBoolean(data[3]);

                    rooms.add(
                            new Room(
                                    roomNumber,
                                    category,
                                    price,
                                    available
                            )
                    );
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }

        return rooms;
    }

    public static void saveReservations(
            List<Reservation> reservations) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(RESERVATION_FILE))) {

            for (Reservation reservation : reservations) {

                Customer customer = reservation.getCustomer();
                Room room = reservation.getRoom();

                writer.write(
                        reservation.getBookingId() + "|" +
                                customer.getName() + "|" +
                                customer.getPhone() + "|" +
                                customer.getEmail() + "|" +
                                room.getRoomNumber() + "|" +
                                room.getCategory() + "|" +
                                room.getPrice() + "|" +
                                reservation.getCheckIn() + "|" +
                                reservation.getCheckOut() + "|" +
                                reservation.getTotalAmount() + "|" +
                                reservation.getPaymentStatus() + "|" +
                                reservation.getBookingStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println(
                    "Error saving reservations: "
                            + e.getMessage()
            );
        }
    }

    public static List<Reservation> loadReservations(
            List<Room> rooms) {

        List<Reservation> reservations = new ArrayList<>();

        File file = new File(RESERVATION_FILE);

        if (!file.exists()) {
            return reservations;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 12) {

                    int bookingId =
                            Integer.parseInt(data[0]);

                    String name = data[1];
                    String phone = data[2];
                    String email = data[3];

                    int roomNumber =
                            Integer.parseInt(data[4]);

                    String category = data[5];

                    double price =
                            Double.parseDouble(data[6]);

                    String checkIn = data[7];
                    String checkOut = data[8];

                    double totalAmount =
                            Double.parseDouble(data[9]);

                    String paymentStatus = data[10];
                    String bookingStatus = data[11];

                    Room room = findRoom(
                            rooms,
                            roomNumber
                    );

                    if (room == null) {

                        room = new Room(
                                roomNumber,
                                category,
                                price
                        );
                    }

                    Customer customer =
                            new Customer(
                                    name,
                                    phone,
                                    email
                            );

                    Reservation reservation =
                            new Reservation(
                                    bookingId,
                                    customer,
                                    room,
                                    checkIn,
                                    checkOut,
                                    totalAmount,
                                    paymentStatus,
                                    bookingStatus
                            );

                    reservations.add(reservation);

                    if (bookingStatus.equals("CONFIRMED")) {
                        room.setAvailable(false);
                    }
                }
            }

        } catch (IOException |
                 NumberFormatException e) {

            System.out.println(
                    "Error loading reservations: "
                            + e.getMessage()
            );
        }

        return reservations;
    }

    private static Room findRoom(
            List<Room> rooms,
            int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }
}