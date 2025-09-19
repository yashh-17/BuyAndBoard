package shopnbook.ecommerce;

public class Product {
    private String productId;
    private String name;
    private double price;
    private String category;
    private double rating;

    public Product() {}

    public Product(String productId, String name, double price, String category, double rating) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.rating = rating;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean matchesCategory(String categoryInput) {
        return this.category != null && this.category.equalsIgnoreCase(categoryInput);
    }

    @Override
    public String toString() {
        String id = productId != null ? productId : "-";
        String cat = category != null ? category : "-";
        return String.format("ID: %s | Name: %s | $%.2f | Category: %s | Rating: %.1f",
                id, name, price, cat, rating);
    }
}
