package shopnbook.ecommerce;

import shopnbook.utils.CurrencyUtils;
import shopnbook.utils.PurchaseCollector;
import shopnbook.ticketbooking.Event;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Cart {
    private Map<Product, Integer> items = new LinkedHashMap<>();
    private List<FlightBooking> flightBookings = new ArrayList<>();
    private User user;

    public Cart(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public List<FlightBooking> getFlightBookings() {
        return flightBookings;
    }

    public User getUser() {
        return user;
    }

    public void addToCart(Product product, int qty) {
        items.put(product, items.getOrDefault(product, 0) + qty);
        System.out.println(product.getName() + " added to cart x" + qty);

        createPendingOrder();
    }

    public void addFlightBooking(Event flight, String passenger, String seat, double price) {
        FlightBooking booking = new FlightBooking(flight, passenger, seat, price);
        flightBookings.add(booking);
        System.out.println("✅ Flight " + flight.getFlightId() + " added to cart for " + passenger);

        createPendingOrder();
    }

    private void createPendingOrder() {
        if (items.isEmpty() && flightBookings.isEmpty()) {
            PurchaseCollector.getInstance().clearPendingOrders();
            return;
        }

        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }

        for (FlightBooking booking : flightBookings) {
            total += booking.getPrice();
        }

        Map<Product, Integer> productSnapshot = new LinkedHashMap<>(items);
        List<FlightBooking> flightSnapshot = new ArrayList<>(flightBookings);

        Order pendingOrder = new Order(productSnapshot, flightSnapshot, total);
        pendingOrder.setStatus(Order.OrderStatus.PENDING);

        PurchaseCollector collector = PurchaseCollector.getInstance();

        collector.clearPendingOrders();
        collector.addOrder(pendingOrder);
    }

    public void removeFromCart(Product product) {
        items.remove(product);
        System.out.println(product.getName() + " removed from cart");
        createPendingOrder();
    }

    public void removeFlightBooking(Event flight) {
        flightBookings.removeIf(booking -> booking.getFlight().equals(flight));
        System.out.println("Flight " + flight.getFlightId() + " removed from cart");
        createPendingOrder(); 
    }

    public void viewCart() {
        if (items.isEmpty() && flightBookings.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        double ecomTotal = 0.0;
        double flightTotal = 0.0;

        System.out.println("\n🛒 UNIFIED CART");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Show E-commerce items
        if (!items.isEmpty()) {
            System.out.println("🛍️ E-COMMERCE ITEMS:");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                double line = p.getPrice() * qty;
                ecomTotal += line;
                System.out.println("  " + p.getName() + " x" + qty + " = " + CurrencyUtils.formatPrice(line));
            }
        }

        if (!flightBookings.isEmpty()) {
            System.out.println("\n✈️ FLIGHT TICKETS:");
            for (FlightBooking booking : flightBookings) {
                flightTotal += booking.getPrice();
                System.out.println("  " + booking.getFlight().getFlightId() + " (" +
                    booking.getFlight().getOrigin() + " → " + booking.getFlight().getDestination() + ") = " +
                    CurrencyUtils.formatPrice(booking.getPrice()));
            }
        }

        double grandTotal = ecomTotal + flightTotal;
        System.out.println("\n💰 CART TOTALS:");
        if (ecomTotal > 0) {
            System.out.println("  E-commerce: " + CurrencyUtils.formatPrice(ecomTotal));
        }
        if (flightTotal > 0) {
            System.out.println("  Flight Tickets: " + CurrencyUtils.formatPrice(flightTotal));
        }
        System.out.println("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Grand Total: " + CurrencyUtils.formatPrice(grandTotal));
    }

    public Order placeOrder(DeliveryDetails delivery) {
        if (items.isEmpty() && flightBookings.isEmpty()) {
            System.out.println("Nothing to order");
            return null;
        }

        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }

        for (FlightBooking booking : flightBookings) {
            total += booking.getPrice();
        }

        Map<Product, Integer> productSnapshot = new LinkedHashMap<>(items);
        List<FlightBooking> flightSnapshot = new ArrayList<>(flightBookings);

        Order confirmedOrder = new Order(productSnapshot, flightSnapshot, total);
        confirmedOrder.setStatus(Order.OrderStatus.CONFIRMED);
        if (delivery != null) {
            confirmedOrder.setDeliveryDetails(delivery);
        }

        items.clear();
        flightBookings.clear();
        createPendingOrder(); 
        System.out.println("✅ Order placed successfully: #" + confirmedOrder.getOrderId());
        System.out.println("   E-commerce: " + CurrencyUtils.formatPrice(getEcomTotal(productSnapshot)));
        System.out.println("   Flight Tickets: " + CurrencyUtils.formatPrice(getFlightTotal(flightSnapshot)));
        System.out.println("   Total Paid: " + CurrencyUtils.formatPrice(total));

        PurchaseCollector.getInstance().addOrder(confirmedOrder);

        return confirmedOrder;
    }

    private double getEcomTotal(Map<Product, Integer> products) {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    private double getFlightTotal(List<FlightBooking> flights) {
        double total = 0.0;
        for (FlightBooking booking : flights) {
            total += booking.getPrice();
        }
        return total;
    }
}


