package shopnbook.ecommerce;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EcommerceApp {
    public static void start() {
        System.out.println("Welcome to E-Commerce Shopping!");
        // Initialize user and catalog
        User user = new User("Alice", 2000.00);
        ProductCatalog.loadSampleProducts();

        Cart cart = new Cart(user);
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
                    Order order = cart.placeOrder();
                    if (order != null) {
                        System.out.println(order.toString());
                        System.out.println("Remaining wallet balance: $" + String.format("%.2f", user.getWalletBalance()));
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

        // Build unique category list preserving insertion order
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

    private static int safeNextInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        return sc.nextInt();
    }
}
