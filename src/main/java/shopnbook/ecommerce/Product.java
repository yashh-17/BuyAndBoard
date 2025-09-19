package shopnbook.ecommerce;

public class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int qty) {
        this.stock -= qty;
        if (this.stock < 0) {
            this.stock = 0;
        }
    }

    @Override
    public String toString() {
        return name + " ($" + price + ", stock=" + stock + ")";
    }
}


