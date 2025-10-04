package shopnbook.ecommerce;

import shopnbook.utils.CurrencyUtils;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class Order {
    public enum OrderStatus {
        PENDING, CONFIRMED, FAILED
    }

    private static int nextId = 1;

    private int orderId;
    private Map<Product, Integer> items;
    private List<FlightBooking> flightBookings;
    private double total;
    private OrderStatus status;

    public Order(Map<Product, Integer> items, double total) {
        this(items, new ArrayList<>(), total);
    }

    public Order(Map<Product, Integer> items, List<FlightBooking> flightBookings, double total) {
        this.orderId = nextId++;
        // Create copies to prevent external modifications
        this.items = new LinkedHashMap<>(items);
        this.flightBookings = new ArrayList<>(flightBookings);
        this.total = total;
        this.status = OrderStatus.CONFIRMED; // Default to confirmed for now
    }

    public int getOrderId() {
        return orderId;
    }

    public Map<Product, Integer> getItems() {
        return new LinkedHashMap<>(items); // Return a copy to prevent external modifications
    }

    public List<FlightBooking> getFlightBookings() {
        return new ArrayList<>(flightBookings); // Return a copy to prevent external modifications
    }

    public double getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append(" (").append(status).append(")\n");

        // Show e-commerce items
        if (!items.isEmpty()) {
            sb.append("E-commerce Items:\n");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                sb.append(" - ").append(p.getName()).append(" x").append(qty)
                  .append(" = ").append(CurrencyUtils.formatPrice(p.getPrice() * qty)).append("\n");
            }
        }

        // Show flight bookings
        if (!flightBookings.isEmpty()) {
            if (!items.isEmpty()) {
                sb.append("\n"); // Add spacing between sections
            }
            sb.append("Flight Tickets:\n");
            for (FlightBooking booking : flightBookings) {
                sb.append(" - ").append(booking.getFlight().getFlightId())
                  .append(" (").append(booking.getPassenger()).append(")")
                  .append(" = ").append(CurrencyUtils.formatPrice(booking.getPrice())).append("\n");
            }
        }

        sb.append("Total: ").append(CurrencyUtils.formatPrice(total));
        return sb.toString();
    }
}


