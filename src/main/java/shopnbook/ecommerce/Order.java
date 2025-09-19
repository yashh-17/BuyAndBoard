package shopnbook.ecommerce;

import java.util.Map;

public class Order {
    private static int nextId = 1;

    private int orderId;
    private Map<Product, Integer> items;
    private double total;

    public Order(Map<Product, Integer> items, double total) {
        this.orderId = nextId++;
        this.items = items;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append("\n");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            sb.append(" - ").append(p.getName()).append(" x").append(qty)
              .append(" = $").append(String.format("%.2f", p.getPrice() * qty)).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", total));
        return sb.toString();
    }
}


