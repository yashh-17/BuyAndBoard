package shopnbook.ecommerce;

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
        User user = new User("Alice");
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
                    manageCart(sc, cart);
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
                        DeliveryDetails delivery = DeliveryService.promptDeliveryDetails(sc, cart, user);

                        // Unified payment prompt (UPI)
                        System.out.println("\nSelect Payment Method:");
                        System.out.println("1. UPI");
                        System.out.print("Enter choice: ");
                        String pmChoice = sc.next().trim();
                        if (!pmChoice.equals("1")) {
                            System.out.println("Unsupported payment method. Cancelling.");
                            break;
                        }

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

                        shopnbook.payment.UpiPaymentService upi = new shopnbook.payment.UpiPaymentService();
                        shopnbook.payment.PaymentResult result = upi.pay(amount, upiId);
                        if (result.getStatus() != shopnbook.payment.PaymentStatus.SUCCESS) {
                            System.out.println("❌ Payment failed via UPI. Please try again later.");
                            break;
                        }

                        System.out.println("✅ Payment successful via UPI!");

                        Order order = cart.placeOrder(delivery);
                        if (order != null) {
                            order.setPaymentMethod(shopnbook.payment.PaymentMethod.UPI);
                            order.setPaymentStatus(shopnbook.payment.PaymentStatus.SUCCESS);
                            order.setTransactionId(result.getTransactionId());
                            order.setPaidAt(java.time.LocalDateTime.now());
                            System.out.println("Order placed successfully!");
                            System.out.println("Your tickets have been booked successfully and will be sent to your registered email.");
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
    //

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

        while (true) {
            System.out.println("\nCategories:");
            for (int i = 0; i < categoryList.size(); i++) {
                System.out.println((i + 1) + ". " + categoryList.get(i));
            }
            System.out.println("0. Back");
            System.out.print("Select category: ");

            clearInputBuffer(sc);
            String input = sc.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= categoryList.size()) {
                    String selectedCategory = categoryList.get(choice - 1);
                    showCategoryProducts(sc, selectedCategory, cart);
                    break;
                } else if (choice == 0) {
                    return; // Go back to main menu
                } else {
                    System.out.println("❌ Invalid category! Please choose a number between 1 and " + categoryList.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid category! Please choose a number between 1 and " + categoryList.size() + ".");
            }
        }
    }

    private static void showCategoryProducts(Scanner sc, String selectedCategory, Cart cart) {
        List<Product> filtered = ProductCatalog.filterByCategory(selectedCategory);
        if (filtered.isEmpty()) {
            System.out.println("No products under this category.");
            return;
        }

        while (true) {
            System.out.println("\nProducts in '" + selectedCategory + "':");
            for (int i = 0; i < filtered.size(); i++) {
                Product p = filtered.get(i);
                System.out.println((i + 1) + ". " + p.toString());
            }
            System.out.println("0. Back to Categories");
            System.out.print("Select product to add : ");

            String input = sc.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= filtered.size()) {
                    Product selectedProduct = filtered.get(choice - 1);

                    System.out.print("Quantity: ");
                    String qtyInput = sc.nextLine().trim();

                    try {
                        int qty = Integer.parseInt(qtyInput);
                        if (qty <= 0) {
                            System.out.println("❌ Add atleast one item");
                            continue;
                        }

                        cart.addToCart(selectedProduct, qty);
                        System.out.println("✅ Added " + qty + " x " + selectedProduct.getName() + " to cart!");
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid quantity! Please enter a valid number.");
                    }
                } else if (choice == 0) {
                    return; // Go back to categories
                } else {
                    System.out.println("❌ Invalid product! Please choose a number between 1 and " + filtered.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid product! Please choose a number between 1 and " + filtered.size() + ".");
            }
        }
    }

    private static void manageCart(Scanner sc, Cart cart) {
        while (true) {
            System.out.println("\n🛒 CART MANAGEMENT");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            if (cart.getItems().isEmpty() && cart.getFlightBookings().isEmpty()) {
                System.out.println("Your cart is empty.");
                System.out.println("0. Back to Previous Menu");
                System.out.print("Select option: ");

                String input = sc.nextLine().trim();
                try {
                    int choice = Integer.parseInt(input);
                    if (choice == 0) {
                        return;
                    } else {
                        System.out.println("❌ Invalid option! Please choose a valid menu number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid option! Please choose a valid menu number.");
                }
                continue;
            }

            // Display cart contents
            cart.viewCart();

            System.out.println("\n📋 CART OPTIONS:");
            System.out.println("1. Remove E-commerce item");
            System.out.println("2. Modify item quantity");
            if (!cart.getFlightBookings().isEmpty()) {
                System.out.println("3. Remove flight booking");
            }
            System.out.println("0. Back to Previous Menu");
            System.out.print("Select option: ");

            String input = sc.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 1) {
                    removeEcommerceItem(sc, cart);
                } else if (choice == 2) {
                    modifyItemQuantity(sc, cart);
                } else if (choice == 3 && !cart.getFlightBookings().isEmpty()) {
                    removeFlightBooking(sc, cart);
                } else if (choice == 0) {
                    return;
                } else {
                    System.out.println("❌ Invalid option! Please choose a valid menu number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid option! Please choose a valid menu number.");
            }
        }
    }

    private static void removeEcommerceItem(Scanner sc, Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("No e-commerce items to remove.");
            return;
        }

        System.out.println("\n🛍️ E-COMMERCE ITEMS:");
        int index = 1;
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(index + ". " + p.getName() + " x" + qty);
            index++;
        }

        System.out.print("Enter item number to remove (0 to cancel): ");
        String input = sc.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);

            if (choice == 0) {
                return;
            } else if (choice >= 1 && choice <= cart.getItems().size()) {
                Product[] products = cart.getItems().keySet().toArray(new Product[0]);
                Product itemToRemove = products[choice - 1];
                cart.removeFromCart(itemToRemove);
            } else {
                System.out.println("❌ Invalid option! Please choose a valid menu number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid option! Please choose a valid menu number.");
        }
    }

    private static void modifyItemQuantity(Scanner sc, Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("No e-commerce items to modify.");
            return;
        }

        System.out.println("\n🛍️ E-COMMERCE ITEMS:");
        int index = 1;
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(index + ". " + p.getName() + " x" + qty);
            index++;
        }

        System.out.print("Enter item number to modify (0 to cancel): ");
        String input = sc.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);

            if (choice == 0) {
                return;
            } else if (choice >= 1 && choice <= cart.getItems().size()) {
                Product[] products = cart.getItems().keySet().toArray(new Product[0]);
                Product itemToModify = products[choice - 1];

                System.out.print("Enter new quantity: ");
                String qtyInput = sc.nextLine().trim();

                try {
                    int newQty = Integer.parseInt(qtyInput);
                    if (newQty <= 0) {
                        System.out.println("❌ Invalid input! Please enter a positive number.");
                        return;
                    }

                    cart.removeFromCart(itemToModify); // Remove current quantity

                    if (newQty > 0) {
                        cart.addToCart(itemToModify, newQty); // Add new quantity
                        System.out.println("✅ Quantity updated to " + newQty);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input! Please enter a valid number.");
                }
            } else {
                System.out.println("❌ Invalid option! Please choose a valid menu number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid option! Please choose a valid menu number.");
        }
    }

    private static void removeFlightBooking(Scanner sc, Cart cart) {
        if (cart.getFlightBookings().isEmpty()) {
            System.out.println("No flight bookings to remove.");
            return;
        }

        System.out.println("\n✈️ FLIGHT BOOKINGS:");
        int index = 1;
        for (FlightBooking booking : cart.getFlightBookings()) {
            System.out.println(index + ". " + booking.getFlight().getFlightId() + " (" +
                booking.getFlight().getOrigin() + " → " + booking.getFlight().getDestination() + ")");
            index++;
        }

        System.out.print("Enter booking number to remove (0 to cancel): ");
        String input = sc.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);

            if (choice == 0) {
                return;
            } else if (choice >= 1 && choice <= cart.getFlightBookings().size()) {
                FlightBooking[] bookings = cart.getFlightBookings().toArray(new FlightBooking[0]);
                FlightBooking bookingToRemove = bookings[choice - 1];
                cart.removeFlightBooking(bookingToRemove.getFlight());
            } else {
                System.out.println("❌ Invalid option! Please choose a valid menu number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid option! Please choose a valid menu number.");
        }
    }

    private static void clearInputBuffer(Scanner sc) {
        sc.nextLine(); // Consume any remaining newline
    }

    private static int safeNextInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        return sc.nextInt();
    }

    // Basic UPI ID validation: something@provider
    private static boolean isValidUpiId(String upiId) {
        if (upiId == null) return false;
        String trimmed = upiId.trim();
        if (trimmed.length() < 5) return false;
        int at = trimmed.indexOf('@');
        if (at <= 0 || at == trimmed.length() - 1) return false;
        String name = trimmed.substring(0, at);
        String provider = trimmed.substring(at + 1);
        return name.matches("[A-Za-z0-9._-]{2,}") && provider.matches("[A-Za-z]{2,}");
    }
}
