import java.util.Scanner;

public class Payment {

    public static boolean processPayment(double amount, Scanner scanner) {

        System.out.println("\n========== PAYMENT ==========");
        System.out.println("Amount to Pay: ₹" + amount);

        System.out.println("\nSelect Payment Method");
        System.out.println("1. UPI");
        System.out.println("2. Debit Card");
        System.out.println("3. Credit Card");

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > 3) {
            System.out.println("Invalid payment method.");
            return false;
        }

        System.out.print("Enter payment reference/name: ");
        String reference = scanner.nextLine();

        if (reference.isEmpty()) {
            System.out.println("Payment failed.");
            return false;
        }

        System.out.println("\nProcessing payment...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Payment successful!");
        return true;
    }
}