package shopnbook.ecommerce;

import shopnbook.utils.CurrencyUtils;
import shopnbook.utils.TableFormatter;
import shopnbook.ticketbooking.Event;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InvoiceGenerator {

    public static void displayProfessionalInvoice(Order order, User user, DeliveryDetails delivery) {
        System.out.println("\n🧾 PROFESSIONAL INVOICE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Order ID: #" + order.getOrderId());
        System.out.println("Date/Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Customer: " + user.getName());

        if (!order.getItems().isEmpty()) {
            System.out.println("\n🛍️ E-COMMERCE ITEMS");
            List<String> headers = Arrays.asList("Item", "Qty", "Unit Price", "Subtotal");
            List<List<String>> rows = new ArrayList<>();
            double ecomSubtotal = 0.0;
            for (Map.Entry<Product, Integer> entry : order.getItems().entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                double subtotal = p.getPrice() * qty;
                ecomSubtotal += subtotal;
                rows.add(Arrays.asList(p.getName(), String.valueOf(qty), CurrencyUtils.formatPrice(p.getPrice()), CurrencyUtils.formatPrice(subtotal)));
            }
            List<Integer> widths = Arrays.asList(30, 5, 12, 12);
            TableFormatter.printTable(headers, rows, widths);
            System.out.println("E-commerce Subtotal: " + CurrencyUtils.formatPrice(ecomSubtotal));
        }

        if (!order.getFlightBookings().isEmpty()) {
            System.out.println("\n✈️ FLIGHT TICKETS");
            List<String> headers = Arrays.asList("Flight ID", "Route", "Passenger", "Seat", "Price");
            List<List<String>> rows = new ArrayList<>();
            double flightSubtotal = 0.0;
            for (FlightBooking booking : order.getFlightBookings()) {
                Event flight = booking.getFlight();
                flightSubtotal += booking.getPrice();
                rows.add(Arrays.asList(flight.getFlightId(), flight.getOrigin() + " → " + flight.getDestination(), booking.getPassenger(), booking.getSeat(), CurrencyUtils.formatPrice(booking.getPrice())));
            }
            List<Integer> widths = Arrays.asList(10, 20, 15, 8, 12);
            TableFormatter.printTable(headers, rows, widths);
            System.out.println("Flight Subtotal: " + CurrencyUtils.formatPrice(flightSubtotal));
        }

        System.out.println("\n💰 TOTALS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Grand Total: " + CurrencyUtils.formatPrice(order.getTotal()));

        if (delivery != null) {
            System.out.println("\n📦 DELIVERY ADDRESS");
            System.out.println(delivery.toString());
        }

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Thank you for your purchase! 🎉");
    }
}
