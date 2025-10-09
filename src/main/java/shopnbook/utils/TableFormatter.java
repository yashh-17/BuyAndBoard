package shopnbook.utils;

import java.util.List;

public class TableFormatter {

    public static void printTable(List<String> headers, List<List<String>> rows, List<Integer> columnWidths) {
        if (headers.size() != columnWidths.size()) {
            throw new IllegalArgumentException("Headers and column widths must have same size");
        }

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

        System.out.println(topBorder);

        System.out.print("│");
        for (int i = 0; i < headers.size(); i++) {
            System.out.printf(" %-" + (columnWidths.get(i) - 2) + "s │", headers.get(i));
        }
        System.out.println();

        if (!rows.isEmpty()) {
            System.out.println(headerSeparator);
        }

        for (List<String> row : rows) {
            System.out.print("│");
            for (int i = 0; i < row.size(); i++) {
                String value = row.get(i);
                int width = columnWidths.get(i);
                if (value.matches(".*₹.*")) {
                    System.out.printf(" %" + (width - 2) + "s │", value);
                } else {
                    System.out.printf(" %-" + (width - 2) + "s │", value);
                }
            }
            System.out.println();
        }

        System.out.println(bottomBorder);
    }

    public static void printSummaryTable(List<List<String>> rows) {
        List<String> headers = List.of(" Description ", " Amount ");
        List<Integer> widths = List.of(37, 13);
        printTable(headers, rows, widths);
    }

    public static void printOrderTable(List<List<String>> rows) {
        List<String> headers = List.of(" # ", " Order ID ", " Items ", " Total ");
        List<Integer> widths = List.of(4, 28, 8, 10);
        printTable(headers, rows, widths);
    }

    public static void printFlightTable(List<List<String>> rows) {
        List<String> headers = List.of(" # ", " Flight ", " Route ", " Price ");
        List<Integer> widths = List.of(4, 16, 16, 11);
        printTable(headers, rows, widths);
    }
}
