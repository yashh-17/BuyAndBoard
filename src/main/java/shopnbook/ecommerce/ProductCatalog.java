package shopnbook.ecommerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Utility class to manage the in-memory catalog of products.
 * - Holds a static list of Product objects
 * - Provides helpers to load samples, add products, list and filter
 * - Pure logic only (no console/file I/O)
 */
public final class ProductCatalog {
    private static final List<Product> productList = new ArrayList<>();

    private ProductCatalog() { }

    /**
     * Adds a single product to the catalog.
     */
    public static void addProduct(Product product) {
        if (product != null) {
            productList.add(product);
        }
    }

    /**
     * Pre-loads a few sample products into the catalog.
     * Safe to call multiple times; samples will be appended if called again.
     */
    public static void loadSampleProducts() {
        addProduct(new Product("P101", "Laptop", 999.99, "Electronics", 4.5));
        addProduct(new Product("P102", "Headphones", 149.50, "Electronics", 4.2));
        addProduct(new Product("P103", "Mouse", 29.99, "Accessories", 4.0));
        addProduct(new Product("P104", "Backpack", 39.99, "Bags", 4.1));
        addProduct(new Product("P105", "Water Bottle", 12.99, "Accessories", 4.3));
    }

    /**
     * Returns an immutable view of all products currently in the catalog.
     */
    public static List<Product> getAllProducts() {
        return Collections.unmodifiableList(productList);
    }

    /**
     * Returns all products matching the given category (case-insensitive).
     */
    public static List<Product> filterByCategory(String category) {
        List<Product> result = new ArrayList<>();
        if (category == null) {
            return result;
        }
        String target = category.toLowerCase(Locale.ROOT);
        for (Product p : productList) {
            String cat = p.getCategory();
            if (cat != null && cat.toLowerCase(Locale.ROOT).equals(target)) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Optional helper: case-insensitive contains search on product name.
     */
    public static List<Product> searchByName(String nameQuery) {
        List<Product> result = new ArrayList<>();
        if (nameQuery == null) {
            return result;
        }
        String q = nameQuery.toLowerCase(Locale.ROOT);
        for (Product p : productList) {
            String n = p.getName();
            if (n != null && n.toLowerCase(Locale.ROOT).contains(q)) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Optional helper: fetch a product by its ID.
     */
    public static Product getById(String productId) {
        if (productId == null) return null;
        for (Product p : productList) {
            if (productId.equals(p.getProductId())) {
                return p;
            }
        }
        return null;
    }
}


