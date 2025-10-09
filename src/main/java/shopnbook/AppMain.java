package shopnbook;

import shopnbook.ecommerce.EcommerceApp;
import shopnbook.ticketbooking.TicketBookingApp;
import shopnbook.auth.AuthController;
import shopnbook.utils.PurchaseCollector;
import shopnbook.ecommerce.Cart;
import shopnbook.ecommerce.Order;
import shopnbook.utils.CurrencyUtils;
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
            Order order = cart.placeOrder(null); // No delivery for unified order
            if (order != null) {
                System.out.println("\n✅ ORDER PLACED!");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println(order.toString());
                System.out.println("💰 Total: " + CurrencyUtils.formatPrice(order.getTotal()));
            }
        } else {
            System.out.println("Order cancelled. You can continue shopping.");
        }
    }
}
