package shopnbook.utils;

public class CurrencyFormatterFactory {

    public static CurrencyFormatter getFormatter(String currencyCode) {
        switch (currencyCode.toUpperCase()) {
            case "INR":
                return new INRFormatter();
            default:
                return new INRFormatter();
        }
    }

    public static CurrencyFormatter getINRFormatter() {
        return new INRFormatter();
    }
}
