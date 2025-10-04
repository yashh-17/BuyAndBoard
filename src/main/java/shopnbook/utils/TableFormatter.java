package shopnbook.utils;

import java.util.List;

/**
 * Utility class for creating perfectly aligned console tables
 * Provides consistent formatting across the entire application
 */
public class TableFormatter {

    /**
     * Creates a perfectly aligned table with consistent formatting
     * @param headers Column headers
     * @param rows Table data rows
     * @param columnWidths Width for each column (including padding)
     */
    public static void printTable(List<String> headers, List<List<String>> rows, List<Integer> columnWidths) {
        if (headers.size() != columnWidths.size()) {
            throw new IllegalArgumentException("Headers and column widths must have same size");
        }

        // Create table borders
        StringBuilder topBorder = new StringBuilder("┌");
        StringBuilder headerSeparator = new StringBuilder("├");
        StringBuilder bottomBorder = new StringBuilder("└");

        for (int i = 0; i < headers.size(); i++) {
            int width = columnWidths.get(i);
            for (int j = 0; j < width; j++) {
                topBorder.append("─");
                headerSeparator.append("─");
                bottomBorder.append("─");
            }
            if (i < headers.size() - 1) {
                topBorder.append("┬");
                headerSeparator.append("┼");
                bottomBorder.append("┴");
            }
        }
        topBorder.append("┐");
        headerSeparator.append("┤");
        bottomBorder.append("┘");

        // Print table
        System.out.println(topBorder);

        // Print headers
        System.out.print("│");
        for (int i = 0; i < headers.size(); i++) {
            System.out.printf(" %-" + (columnWidths.get(i) - 2) + "s │", headers.get(i));
        }
        System.out.println();

        // Print separator (only if there are data rows)
        if (!rows.isEmpty()) {
            System.out.println(headerSeparator);
        }

        // Print data rows
        for (List<String> row : rows) {
            System.out.print("│");
            for (int i = 0; i < row.size(); i++) {
                String value = row.get(i);
                int width = columnWidths.get(i);
                // Right-align currency amounts, left-align text
                if (value.matches(".*₹.*")) {
                    System.out.printf(" %" + (width - 2) + "s │", value);
                } else {
                    System.out.printf(" %-" + (width - 2) + "s │", value);
                }
            }
            System.out.println();
        }

        // Print bottom border
        System.out.println(bottomBorder);
    }

    /**
     * Convenience method for 2-column tables (Description | Amount)
     */
    public static void printSummaryTable(List<List<String>> rows) {
        List<String> headers = List.of(" Description ", " Amount ");
        List<Integer> widths = List.of(37, 13);
        printTable(headers, rows, widths);
    }

    /**
     * Convenience method for order tables
     */
    public static void printOrderTable(List<List<String>> rows) {
        List<String> headers = List.of(" # ", " Order ID ", " Items ", " Total ");
        List<Integer> widths = List.of(4, 28, 8, 10);
        printTable(headers, rows, widths);
    }

    /**
     * Convenience method for flight tables
     */
    public static void printFlightTable(List<List<String>> rows) {
        List<String> headers = List.of(" # ", " Flight ", " Route ", " Price ");
        List<Integer> widths = List.of(4, 16, 16, 11);
        printTable(headers, rows, widths);
    }
}
