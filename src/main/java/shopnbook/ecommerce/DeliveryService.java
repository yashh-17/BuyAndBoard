package shopnbook.ecommerce;

import java.util.Scanner;

public class DeliveryService {

    public static DeliveryDetails promptDeliveryDetails(Scanner sc, Cart cart, User user) {
        boolean hasEcomItems = !cart.getItems().isEmpty();
        boolean hasFlights = !cart.getFlightBookings().isEmpty();

        if (!hasEcomItems && hasFlights) {
            System.out.println("No delivery needed for digital flight tickets.");
            return null;
        }

        if (hasEcomItems) {
            System.out.println("\n📦 DELIVERY DETAILS (for E-commerce items)");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            String defaultName = user.getName();
            System.out.print("Name (default: " + defaultName + "): ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) name = defaultName;

            String street = getValidatedInput(sc, "Street Address");
            String city = getValidatedInput(sc, "City");
            String postalCode = getValidatedPostalCode(sc);
            String country = getValidatedInput(sc, "Country");

            System.out.print("Contact Number (optional, press Enter to skip): ");
            String contact = sc.nextLine().trim();
            if (contact.isEmpty()) contact = null;

            return new DeliveryDetails(name, street, city, postalCode, country, contact);
        }

        return null;
    }

    private static String getValidatedInput(Scanner sc, String field) {
        while (true) {
            System.out.print(field + ": ");
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("❌ " + field + " cannot be empty. Please enter a value.");
        }
    }

    private static String getValidatedPostalCode(Scanner sc) {
        while (true) {
            System.out.print("Postal Code: ");
            String input = sc.nextLine().trim();
            if (input.matches("\\d{5,6}")) {
                return input;
            }
            System.out.println("❌ Invalid postal code. Please enter 5-6 digits.");
        }
    }
}
