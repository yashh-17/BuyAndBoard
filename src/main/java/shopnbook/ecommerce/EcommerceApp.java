package shopnbook.ecommerce;

import shopnbook.utils.CurrencyUtils;
import shopnbook.utils.PurchaseCollector;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class EcommerceApp {
    public static void start() {
        System.out.println("Welcome to E-Commerce Shopping!");
        User user = new User("Alice", 2000.00);
        ProductCatalog.loadSampleProducts();
        Cart cart = PurchaseCollector.getInstance().getCurrentCart();
        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n=== E-Commerce Menu ===");
            System.out.println("1. Browse by category");
            System.out.println("2. View cart");
            System.out.println("3. Place order");
            System.out.println("0. Back to main menu");
            System.out.print("Select option: ");
            int choice = safeNextInt(sc);

            switch (choice) {
                case 1:
                    browseByCategory(sc, cart);
                    break;
                case 2:
                    cart.viewCart();
                    break;
                case 3:
                    if (cart.getItems().isEmpty() && cart.getFlightBookings().isEmpty()) {
                        System.out.println("Your cart is empty. Add some items first!");
                        break;
                    }
                    System.out.println("\n🛒 ORDER CONFIRMATION");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    cart.viewCart();
                    System.out.println("\n📋 CURRENT PURCHASE SUMMARY:");
                    PurchaseCollector.getInstance().displayPurchaseSummary();
                    System.out.println("\n❓ Proceed to payment? (Y/N): ");
                    String confirmation = sc.next().trim().toUpperCase();

                    if (confirmation.equals("Y") || confirmation.equals("YES")) {
                        boolean paymentSuccess = processPayment(sc, cart, user);
                        if (paymentSuccess) {
                            Order order = cart.placeOrder();
                            if (order != null) {
                                System.out.println("\n✅ PAYMENT SUCCESSFUL!");
                                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                                System.out.println(order.toString());
                                System.out.println("💰 Total Paid: " + CurrencyUtils.formatPrice(order.getTotal()));
                            }
                        }
                    } else {
                        System.out.println("Order cancelled. You can continue shopping.");
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void browseByCategory(Scanner sc, Cart cart) {
        List<Product> all = ProductCatalog.getAllProducts();
        if (all.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        Set<String> categories = new LinkedHashSet<>();
        for (Product p : all) {
            if (p.getCategory() != null) categories.add(p.getCategory());
        }
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        List<String> categoryList = new ArrayList<>(categories);
        System.out.println("\nCategories:");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i));
        }
        System.out.print("Select category: ");
        int idx = safeNextInt(sc) - 1;
        if (idx < 0 || idx >= categoryList.size()) {
            System.out.println("Invalid category selection");
            return;
        }

        String selectedCategory = categoryList.get(idx);
        List<Product> filtered = ProductCatalog.filterByCategory(selectedCategory);
        if (filtered.isEmpty()) {
            System.out.println("No products under this category.");
            return;
        }

        System.out.println("\nProducts in '" + selectedCategory + "':");
        for (int i = 0; i < filtered.size(); i++) {
            Product p = filtered.get(i);
            System.out.println((i + 1) + ". " + p.toString());
        }

        System.out.print("Select product to add (0 to cancel): ");
        int pidx = safeNextInt(sc);
        if (pidx == 0) return;
        pidx -= 1;
        if (pidx < 0 || pidx >= filtered.size()) {
            System.out.println("Invalid product selection");
            return;
        }

        System.out.print("Quantity: ");
        int qty = safeNextInt(sc);
        if (qty <= 0) {
            System.out.println("Quantity must be positive");
            return;
        }

        cart.addToCart(filtered.get(pidx), qty);
    }

    public static boolean processPayment(Scanner sc, Cart cart, User user) {
        return processPayment(sc, cart, user, -1.0);
    }

    public static boolean processPayment(Scanner sc, Cart cart, User user, double amount) {
        double total = 0.0;

        if (cart != null) {
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                total += entry.getKey().getPrice() * entry.getValue();
            }
        } else if (amount > 0) {
            total = amount;
        } else {
            System.out.println("❌ Invalid payment configuration.");
            return false;
        }

        System.out.println("\n💳 PAYMENT OPTIONS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. 💳 Credit/Debit Card");
        System.out.println("2. 📱 UPI (Unified Payments Interface)");
        System.out.println("4. 💰 Wallet (Current Balance: " + CurrencyUtils.formatPrice(user.getWalletBalance()) + ")");
        System.out.println("0. ❌ Cancel Payment");
        System.out.print("Select payment method: ");

        int paymentChoice = safeNextInt(sc);

        switch (paymentChoice) {
            case 1:
                return processCardPayment(sc, total, user);
            case 2:
                return processUPIPayment(sc, total, user);
            case 4:
                return processWalletPayment(total, user);
            case 0:
                System.out.println("Payment cancelled.");
                return false;
            default:
                System.out.println("Invalid payment method selected.");
                return false;
        }
    }

    private static boolean processCardPayment(Scanner sc, double amount, User user) {
        System.out.println("\n💳 CREDIT/DEBIT CARD PAYMENT");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print("Enter card number (16 digits): ");
        String cardNumber = sc.next();

        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            System.out.println("❌ Invalid card number. Payment failed.");
            return false;
        }

        System.out.print("Enter expiry date (MM/YY): ");
        String expiry = sc.next();

        if (!expiry.matches("\\d{2}/\\d{2}")) {
            System.out.println("❌ Invalid expiry date format. Payment failed.");
            return false;
        }

        System.out.print("Enter CVV (3 digits): ");
        String cvv = sc.next();

        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            System.out.println("❌ Invalid CVV. Payment failed.");
            return false;
        }

        System.out.println("🔄 Processing card payment...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("✅ Card payment successful!");
        System.out.println("Amount paid: " + CurrencyUtils.formatPrice(amount));
        return true;
    }

    private static boolean processUPIPayment(Scanner sc, double amount, User user) {
        System.out.println("\n📱 UPI PAYMENT");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print("Enter UPI ID (e.g., user@paytm): ");
        String upiId = sc.next();

        if (!upiId.contains("@")) {
            System.out.println("❌ Invalid UPI ID format. Payment failed.");
            return false;
        }

        System.out.println("🔄 Processing UPI payment...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("✅ UPI payment successful!");
        System.out.println("Amount paid: " + CurrencyUtils.formatPrice(amount));
        return true;
    }

    private static boolean processWalletPayment(double amount, User user) {
        System.out.println("\n💰 WALLET PAYMENT");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (user.getWalletBalance() < amount) {
            System.out.println("❌ Insufficient wallet balance. Current balance: " + CurrencyUtils.formatPrice(user.getWalletBalance()));
            System.out.println("   Required: " + CurrencyUtils.formatPrice(amount));
            return false;
        }

        System.out.println("Current wallet balance: " + CurrencyUtils.formatPrice(user.getWalletBalance()));
        System.out.println("Amount to pay: " + CurrencyUtils.formatPrice(amount));
        System.out.print("Confirm wallet payment? (Y/N): ");

        Scanner sc = new Scanner(System.in);
        String confirm = sc.next().trim().toUpperCase();

        if (confirm.equals("Y") || confirm.equals("YES")) {
            user.deductBalance(amount);
            System.out.println("✅ Wallet payment successful!");
            return true;
        } else {
            System.out.println("❌ Wallet payment cancelled.");
            return false;
        }
    }

    private static int safeNextInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        return sc.nextInt();
    }
}
