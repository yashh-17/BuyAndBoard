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

            String street = getValidatedInput(sc, "Street");
            System.out.print("Village (optional): ");
            String village = sc.nextLine().trim();
            if (village.isEmpty()) village = null;
            String city = getValidatedInput(sc, "City");
            String state = getValidatedInput(sc, "State");
            String pin = getValidatedPinCode(sc);
            String country = getValidatedInput(sc, "Country");
            String phone = getValidatedPhone(sc);

            return new DeliveryDetails(street, village, city, state, pin, country, phone);
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

    private static String getValidatedPinCode(Scanner sc) {
        while (true) {
            System.out.print("Pin Code: ");
            String input = sc.nextLine().trim();
            if (input.matches("\\d{5,6}")) {
                return input;
            }
            System.out.println("❌ Invalid pin code. Please enter 5-6 digits.");
        }
    }

    private static String getValidatedPhone(Scanner sc) {
        while (true) {
            System.out.print("Phone Number (10 digits): ");
            String input = sc.nextLine().trim();
            if (input.matches("\\d{10}")) {
                return input;
            }
            System.out.println("❌ Invalid phone number. Please enter exactly 10 digits.");
        }
    }
}
