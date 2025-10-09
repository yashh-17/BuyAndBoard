package shopnbook;

import shopnbook.ecommerce.EcommerceApp;
import shopnbook.ticketbooking.TicketBookingApp;
import shopnbook.auth.AuthController;
import shopnbook.utils.PurchaseCollector;
import shopnbook.ecommerce.Cart;
import shopnbook.ecommerce.Order;
import shopnbook.utils.CurrencyUtils;
import shopnbook.payment.PaymentMethod;
import shopnbook.payment.PaymentStatus;
import shopnbook.payment.PaymentResult;
import shopnbook.payment.UpiPaymentService;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to BuyAndBoard!");
        if (!AuthController.requireAuth(sc)) return;
        boolean running = true;
        while (running) {
            System.out.println("1. E-Commerce Shopping");
            System.out.println("2. Ticket Booking");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. View Purchase Summary");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            int choice = sc.nextInt();

            switch(choice) {
                case 1:
                    EcommerceApp.start();
                    break;
                case 2:
                    TicketBookingApp.start(PurchaseCollector.getInstance().getCurrentUser());
                    break;
                case 3:
                    PurchaseCollector.getInstance().getCurrentCart().viewCart();
                    break;
                case 4:
                    placeOrder(sc);
                    break;
                case 5:
                    PurchaseCollector.getInstance().displayPurchaseSummary();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void placeOrder(Scanner sc) {
        Cart cart = PurchaseCollector.getInstance().getCurrentCart();

        if (cart.getItems().isEmpty() && cart.getFlightBookings().isEmpty()) {
            System.out.println("Your cart is empty. Add some items or book flights first!");
            return;
        }

        System.out.println("\n🛒 ORDER CONFIRMATION");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        cart.viewCart();

        System.out.println("\n📋 CURRENT PURCHASE SUMMARY:");
        PurchaseCollector.getInstance().displayPurchaseSummary();

        System.out.println("\n❓ Proceed to payment? (Y/N): ");
        String confirmation = sc.next().trim().toUpperCase();

        if (confirmation.equals("Y") || confirmation.equals("YES")) {
            // Ask for payment method (UPI only for now)
            System.out.println("\nSelect Payment Method:");
            System.out.println("1. UPI");
            System.out.print("Enter choice: ");
            String pmChoice = sc.next().trim();
            if (!pmChoice.equals("1")) {
                System.out.println("Unsupported payment method. Cancelling.");
                return;
            }

            // Collect and validate UPI ID
            sc.nextLine(); // consume endline
            String upiId;
            while (true) {
                System.out.print("Enter your UPI ID (e.g., name@bank): ");
                upiId = sc.nextLine().trim();
                if (isValidUpiId(upiId)) break;
                System.out.println("❌ Invalid UPI ID. Please use format like 'name@bank'.");
            }

            double amount = PurchaseCollector.getInstance().getTotalCartValue();
            System.out.println("\n✅ Payment request sent to your UPI app. Please check your phone and approve the payment.");

            UpiPaymentService upi = new UpiPaymentService();
            PaymentResult result = upi.pay(amount, upiId);
            if (result.getStatus() != PaymentStatus.SUCCESS) {
                System.out.println("❌ Payment failed via UPI. Please try again later.");
                return;
            }

            System.out.println("✅ Payment successful via UPI!");

            Order order = cart.placeOrder(null); // No delivery for unified order
            if (order != null) {
                order.setPaymentMethod(PaymentMethod.UPI);
                order.setPaymentStatus(PaymentStatus.SUCCESS);
                order.setTransactionId(result.getTransactionId());
                order.setPaidAt(LocalDateTime.now());

                System.out.println("\n✅ ORDER PLACED!");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println(order.toString());
                System.out.println("💰 Total: " + CurrencyUtils.formatPrice(order.getTotal()));
                System.out.println("Your tickets have been booked successfully and will be sent to your registered email.");
            }
        } else {
            System.out.println("Order cancelled. You can continue shopping.");
        }
    }

    // Basic UPI ID validation: something@provider
    private static boolean isValidUpiId(String upiId) {
        if (upiId == null) return false;
        String trimmed = upiId.trim();
        if (trimmed.length() < 5) return false;
        int at = trimmed.indexOf('@');
        if (at <= 0 || at == trimmed.length() - 1) return false;
        // simple char checks
        String name = trimmed.substring(0, at);
        String provider = trimmed.substring(at + 1);
        return name.matches("[A-Za-z0-9._-]{2,}") && provider.matches("[A-Za-z]{2,}");
    }
}
