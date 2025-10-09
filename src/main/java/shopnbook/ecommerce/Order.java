package shopnbook.ecommerce;

import shopnbook.utils.CurrencyUtils;
import shopnbook.payment.PaymentMethod;
import shopnbook.payment.PaymentStatus;
import java.time.LocalDateTime;
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
    private DeliveryDetails deliveryDetails;
    // Payment details
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime paidAt;

    public Order(Map<Product, Integer> items, double total) {
        this(items, new ArrayList<>(), total);
    }

    public Order(Map<Product, Integer> items, List<FlightBooking> flightBookings, double total) {
        this.orderId = nextId++;
        this.items = new LinkedHashMap<>(items);
        this.flightBookings = new ArrayList<>(flightBookings);
        this.total = total;
        this.status = OrderStatus.CONFIRMED;
    }

    public int getOrderId() {
        return orderId;
    }

    public Map<Product, Integer> getItems() {
        return new LinkedHashMap<>(items);
    }

    public List<FlightBooking> getFlightBookings() {
        return new ArrayList<>(flightBookings);
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

    public DeliveryDetails getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public double getEcomSubtotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public double getFlightSubtotal() {
        double total = 0.0;
        for (FlightBooking booking : flightBookings) {
            total += booking.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append(" (").append(status).append(")\n");

        if (!items.isEmpty()) {
            sb.append("E-commerce Items:\n");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                sb.append(" - ").append(p.getName()).append(" x").append(qty)
                  .append(" = ").append(CurrencyUtils.formatPrice(p.getPrice() * qty)).append("\n");
            }
        }

        if (!flightBookings.isEmpty()) {
            if (!items.isEmpty()) {
                sb.append("\n");
            }
            sb.append("Flight Tickets:\n");
            for (FlightBooking booking : flightBookings) {
                sb.append(" - ").append(booking.getFlight().getFlightId())
                  .append(" (").append(booking.getPassenger()).append(")")
                  .append(" = ").append(CurrencyUtils.formatPrice(booking.getPrice())).append("\n");
            }
        }

        sb.append("Total: ").append(CurrencyUtils.formatPrice(total));
        if (paymentMethod != null) {
            sb.append("\nPaid via ").append(paymentMethod.name());
            if (transactionId != null) sb.append(" (Txn: ").append(transactionId).append(")");
            if (paidAt != null) sb.append(" at ").append(paidAt);
        }
        return sb.toString();
    }
}
