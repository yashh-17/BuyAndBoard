package shopnbook.ecommerce;

import shopnbook.utils.CurrencyUtils;

public class Product {
    private String productId;
    private String name;
    private double price;
    private String category;
    private String subCategory;
    private double rating;

    public Product() {}

    public Product(String productId, String name, double price, String category, String subCategory, double rating) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.subCategory = subCategory;
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

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return String.format(
            "ID: %s | %s | %s | Category: %s | SubCategory: %s | Rating: %.1f",
            productId != null ? productId : "-",
            name,
            CurrencyUtils.formatPrice(price),
            category != null ? category : "-",
            subCategory != null ? subCategory : "-",
            rating
        );
    }
}
