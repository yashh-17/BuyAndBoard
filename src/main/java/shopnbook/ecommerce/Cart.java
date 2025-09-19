package shopnbook.ecommerce;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items = new LinkedHashMap<>();
    private User user;

    public Cart(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public void addToCart(Product product, int qty) {
        if (product.getStock() >= qty) {
            items.put(product, items.getOrDefault(product, 0) + qty);
            System.out.println(product.getName() + " added to cart x" + qty);
        } else {
            System.out.println("Not enough stock");
        }
    }

    public void removeFromCart(Product product) {
        items.remove(product);
        System.out.println(product.getName() + " removed from cart");
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        double total = 0.0;
        System.out.println("\n-- Cart --");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double line = p.getPrice() * qty;
            total += line;
            System.out.println(p.getName() + " x" + qty + " = $" + String.format("%.2f", line));
        }
        System.out.println("Subtotal: $" + String.format("%.2f", total));
    }

    public Order placeOrder() {
        if (items.isEmpty()) {
            System.out.println("Nothing to order");
            return null;
        }

        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }

        if (user.getWalletBalance() < total) {
            System.out.println("Insufficient wallet balance");
            return null;
        }

        // Deduct balance
        user.deductBalance(total);

        // Reduce stock
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }

        // Create order with a shallow copy of items to keep record
        Map<Product, Integer> snapshot = new LinkedHashMap<>(items);

        // Clear cart
        items.clear();

        Order order = new Order(snapshot, total);
        System.out.println("Order placed successfully: #" + order.getOrderId());
        return order;
    }
}


