package shopnbook.utils;

public class CurrencyUtils {
    public static final String CURRENCY_SYMBOL = "₹";
    public static final String CURRENCY_CODE = "INR";

    public static String formatPrice(double amount) {
        return CURRENCY_SYMBOL + String.format("%.2f", amount);
    }

    public static String formatPriceShort(double amount) {
        return CURRENCY_SYMBOL + String.format("%.0f", amount);
    }

    public static String formatPriceWithCode(double amount) {
        return CURRENCY_SYMBOL + String.format("%.2f", amount) + " " + CURRENCY_CODE;
    }

    public static double parsePrice(String formattedPrice) {
        if (formattedPrice == null || formattedPrice.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = formattedPrice.replace(CURRENCY_SYMBOL, "").replace(",", "").trim();
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
