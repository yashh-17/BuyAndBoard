package shopnbook.ecommerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class ProductCatalog {
    private static final List<Product> productList = new ArrayList<>();

    private ProductCatalog() {}

    public static void addProduct(Product product) {
        if (product != null && getById(product.getProductId()) == null) {
            productList.add(product);
        }
    }
    public static void loadSampleProducts() {
        if (!productList.isEmpty()) return;

        addProduct(new Product("P101", "Dell Inspiron Laptop", 55000.0, "Electronics", "Laptops", 4.5));
        addProduct(new Product("P102", "HP Pavilion Laptop", 62000.0, "Electronics", "Laptops", 4.6));
        addProduct(new Product("P103", "Apple iPhone 14", 79999.0, "Electronics", "Mobiles", 4.8));
        addProduct(new Product("P104", "Samsung Galaxy S23", 74999.0, "Electronics", "Mobiles", 4.7));
        addProduct(new Product("P105", "Sony WH-1000XM4 Headphones", 24990.0, "Electronics", "Audio", 4.6));
        addProduct(new Product("P106", "Logitech Wireless Keyboard", 2999.0, "Electronics", "Accessories", 4.3));
        addProduct(new Product("P107", "Anker Laptop Charger", 1999.0, "Electronics", "Accessories", 4.2));

        addProduct(new Product("P108", "Nike T-Shirt", 1499.0, "Clothing", "Men", 4.5));
        addProduct(new Product("P109", "Adidas Hoodie", 3299.0, "Clothing", "Men", 4.6));
        addProduct(new Product("P110", "Levi’s Jeans", 3999.0, "Clothing", "Men", 4.4));
        addProduct(new Product("P111", "Zara Dress", 4599.0, "Clothing", "Women", 4.6));
        addProduct(new Product("P112", "H&M Jacket", 4999.0, "Clothing", "Women", 4.3));
        addProduct(new Product("P113", "Puma Shorts", 1799.0, "Clothing", "Sportswear", 4.2));
        addProduct(new Product("P114", "Under Armour Track Pants", 2599.0, "Clothing", "Sportswear", 4.5));

        addProduct(new Product("P115", "Nike Air Sneakers", 6499.0, "Footwear", "Men", 4.6));
        addProduct(new Product("P116", "Adidas Running Shoes", 5999.0, "Footwear", "Men", 4.5));
        addProduct(new Product("P117", "Puma Sandals", 2299.0, "Footwear", "Unisex", 4.1));
        addProduct(new Product("P118", "Crocs Slippers", 1999.0, "Footwear", "Unisex", 4.2));
        addProduct(new Product("P119", "Woodland Boots", 4999.0, "Footwear", "Men", 4.3));
        addProduct(new Product("P120", "Skechers Walking Shoes", 5499.0, "Footwear", "Women", 4.4));

        addProduct(new Product("P121", "LG Refrigerator", 45000.0, "Home Appliances", "Kitchen", 4.6));
        addProduct(new Product("P122", "Samsung Washing Machine", 38000.0, "Home Appliances", "Laundry", 4.5));
        addProduct(new Product("P123", "Philips Mixer Grinder", 3499.0, "Home Appliances", "Kitchen", 4.3));
        addProduct(new Product("P124", "Prestige Electric Kettle", 1499.0, "Home Appliances", "Kitchen", 4.2));
        addProduct(new Product("P125", "Dyson Vacuum Cleaner", 38990.0, "Home Appliances", "Cleaning", 4.7));
        addProduct(new Product("P126", "Bajaj Ceiling Fan", 2899.0, "Home Appliances", "Cooling", 4.1));

        addProduct(new Product("P127", "Atomic Habits", 499.0, "Books", "Self Help", 4.9));
        addProduct(new Product("P128", "Rich Dad Poor Dad", 399.0, "Books", "Finance", 4.8));
        addProduct(new Product("P129", "The Alchemist", 299.0, "Books", "Fiction", 4.7));
        addProduct(new Product("P130", "Clean Code", 999.0, "Books", "Programming", 4.9));
        addProduct(new Product("P131", "Java: Complete Reference", 1299.0, "Books", "Programming", 4.8));
        addProduct(new Product("P132", "Harry Potter Box Set", 3499.0, "Books", "Fiction", 5.0));

        addProduct(new Product("P133", "IKEA Office Chair", 7499.0, "Furniture", "Office", 4.5));
        addProduct(new Product("P134", "Wooden Dining Table", 24999.0, "Furniture", "Dining", 4.6));
        addProduct(new Product("P135", "Queen Size Bed", 45999.0, "Furniture", "Bedroom", 4.7));
        addProduct(new Product("P136", "Sofa Set 3+2", 55999.0, "Furniture", "Living Room", 4.6));
        addProduct(new Product("P137", "Study Desk", 6999.0, "Furniture", "Office", 4.4));
        addProduct(new Product("P138", "Bookshelf Wooden", 8999.0, "Furniture", "Living Room", 4.3));

        addProduct(new Product("P139", "Adidas Football", 1499.0, "Sports", "Outdoor", 4.5));
        addProduct(new Product("P140", "Yonex Badminton Racket", 3499.0, "Sports", "Indoor", 4.6));
        addProduct(new Product("P141", "Nivia Basketball", 1299.0, "Sports", "Outdoor", 4.3));
        addProduct(new Product("P142", "SS Cricket Bat", 7999.0, "Sports", "Outdoor", 4.7));
        addProduct(new Product("P143", "Cosco Volleyball", 899.0, "Sports", "Outdoor", 4.2));
        addProduct(new Product("P144", "Butterfly Table Tennis Bat", 2199.0, "Sports", "Indoor", 4.4));

        addProduct(new Product("P145", "Tata Salt 1kg", 25.0, "Groceries", "Essentials", 4.6));
        addProduct(new Product("P146", "Aashirvaad Atta 5kg", 280.0, "Groceries", "Essentials", 4.7));
        addProduct(new Product("P147", "Fortune Sunflower Oil 1L", 190.0, "Groceries", "Essentials", 4.5));
        addProduct(new Product("P148", "Red Label Tea 500g", 270.0, "Groceries", "Beverages", 4.6));
        addProduct(new Product("P149", "Nescafe Coffee 200g", 520.0, "Groceries", "Beverages", 4.4));
        addProduct(new Product("P150", "Amul Butter 500g", 290.0, "Groceries", "Dairy", 4.8));
    }

    public static List<Product> getAllProducts() {
        return Collections.unmodifiableList(productList);
    }

    public static Product getById(String productId) {
        if (productId == null) return null;
        for (Product p : productList) {
            if (productId.equals(p.getProductId())) return p;
        }
        return null;
    }

    public static List<Product> filterByCategory(String category) {
        List<Product> result = new ArrayList<>();
        if (category == null) return result;
        for (Product p : productList) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }

    public static List<Product> searchByName(String keyword) {
        List<Product> result = new ArrayList<>();
        if (keyword == null) return result;
        for (Product p : productList) {
            if (p.getName().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                result.add(p);
            }
        }
        return result;
    }
}
