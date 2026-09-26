public class Reservation {

    private int bookingId;
    private Customer customer;
    private Room room;
    private String checkIn;
    private String checkOut;
    private double totalAmount;
    private String paymentStatus;
    private String bookingStatus;

    public Reservation(int bookingId,
                       Customer customer,
                       Room room,
                       String checkIn,
                       String checkOut,
                       double totalAmount,
                       String paymentStatus,
                       String bookingStatus) {

        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void displayDetails() {

        System.out.println("\n======================================");
        System.out.println("          BOOKING DETAILS");
        System.out.println("======================================");

        System.out.println("Booking ID     : " + bookingId);

        System.out.println("\nCustomer Details");
        System.out.println("--------------------------------------");
        System.out.println(customer);

        System.out.println("\nRoom Details");
        System.out.println("--------------------------------------");
        System.out.println("Room Number    : " + room.getRoomNumber());
        System.out.println("Category       : " + room.getCategory());
        System.out.println("Price per Night: ₹" + room.getPrice());

        System.out.println("\nStay Details");
        System.out.println("--------------------------------------");
        System.out.println("Check-in       : " + checkIn);
        System.out.println("Check-out      : " + checkOut);

        System.out.println("\nPayment Details");
        System.out.println("--------------------------------------");
        System.out.println("Total Amount   : ₹" + totalAmount);
        System.out.println("Payment Status : " + paymentStatus);

        System.out.println("\nBooking Status : " + bookingStatus);

        System.out.println("======================================");
    }
}