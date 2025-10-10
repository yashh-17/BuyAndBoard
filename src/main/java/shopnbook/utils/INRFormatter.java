package shopnbook.utils;

public class INRFormatter extends CurrencyFormatter {

    public INRFormatter() {
        this.currencySymbol = "₹";
        this.currencyCode = "INR";
        this.decimalPlaces = 2;
    }

    @Override
    public String format(double amount) {
        return currencySymbol + formatDecimal(amount);
    }

    @Override
    public double parse(String formattedPrice) {
        if (formattedPrice == null || formattedPrice.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = cleanAmount(formattedPrice);
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public String formatWithCode(double amount) {
        return currencySymbol + formatDecimal(amount) + " " + currencyCode;
    }

    @Override
    public String formatShort(double amount) {
        return currencySymbol + String.format("%.0f", amount);
    }
}
