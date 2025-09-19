package shopnbook.ecommerce;

public class EcommerceApp {
    public static void start() {
        System.out.println("Welcome to E-Commerce Shopping!");
        // Demo: Create a user, products, add to cart, place order
        User user = new User("Alice", 2000.00);

        Product laptop = new Product("Laptop", 999.99, 5);
        Product headphones = new Product("Headphones", 149.50, 10);
        Product mouse = new Product("Mouse", 29.99, 0); // out of stock example

        Cart cart = new Cart(user);
        cart.addToCart(laptop, 1);
        cart.addToCart(headphones, 2);
        cart.addToCart(mouse, 1); // should print not enough stock

        cart.viewCart();

        Order order = cart.placeOrder();
        if (order != null) {
            System.out.println(order.toString());
            System.out.println("Remaining wallet balance: $" + String.format("%.2f", user.getWalletBalance()));
            System.out.println("Updated stock: ");
            System.out.println(" - " + laptop);
            System.out.println(" - " + headphones);
        }
    }
}
