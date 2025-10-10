package shopnbook.utils;

public abstract class CurrencyFormatter {
    protected String currencySymbol;
    protected String currencyCode;
    protected int decimalPlaces;

    public abstract String format(double amount);
    public abstract double parse(String formattedPrice);
    public abstract String formatWithCode(double amount);
    public abstract String formatShort(double amount);

    public String getCurrencySymbol() { return currencySymbol; }
    public String getCurrencyCode() { return currencyCode; }
    public int getDecimalPlaces() { return decimalPlaces; }

    protected String cleanAmount(String input) {
        if (input == null) return "";
        return input.replace(currencySymbol, "").replace(",", "").trim();
    }

    protected String formatDecimal(double amount) {
        return String.format("%." + decimalPlaces + "f", amount);
    }
}
