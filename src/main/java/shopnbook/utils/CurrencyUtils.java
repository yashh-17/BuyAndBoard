package shopnbook.utils;

public class CurrencyUtils {
    // Global currency configuration
    public static final String CURRENCY_SYMBOL = "₹";
    public static final String CURRENCY_CODE = "INR";

    /**
     * Format price with currency symbol and consistent decimal places
     * @param amount The amount to format
     * @return Formatted string like "₹1,234.56"
     */
    public static String formatPrice(double amount) {
        return CURRENCY_SYMBOL + String.format("%.2f", amount);
    }

    /**
     * Format price for display in tables/lists (shorter format)
     * @param amount The amount to format
     * @return Formatted string like "₹1234.56"
     */
    public static String formatPriceShort(double amount) {
        return CURRENCY_SYMBOL + String.format("%.0f", amount);
    }

    /**
     * Format price with currency code for international contexts
     * @param amount The amount to format
     * @return Formatted string like "₹1,234.56 INR"
     */
    public static String formatPriceWithCode(double amount) {
        return CURRENCY_SYMBOL + String.format("%.2f", amount) + " " + CURRENCY_CODE;
    }

    /**
     * Parse a formatted price string back to double
     * @param formattedPrice String like "₹1,234.56"
     * @return Parsed double value
     */
    public static double parsePrice(String formattedPrice) {
        if (formattedPrice == null || formattedPrice.trim().isEmpty()) {
            return 0.0;
        }
        // Remove currency symbol and any commas
        String cleaned = formattedPrice.replace(CURRENCY_SYMBOL, "").replace(",", "").trim();
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
