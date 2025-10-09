package shopnbook.utils;

import shopnbook.ecommerce.Order;
import shopnbook.ecommerce.Cart;
import shopnbook.ecommerce.User;
import shopnbook.ticketbooking.Ticket;
import shopnbook.utils.CurrencyUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PurchaseCollector {
    private static PurchaseCollector instance;
    private List<Order> orders;
    private List<Ticket> tickets;
    private Cart currentCart;
    private LocalDateTime sessionStart;
    private String currentUser;

    private PurchaseCollector() {
        this.orders = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.sessionStart = LocalDateTime.now();
    }

    public static PurchaseCollector getInstance() {
        if (instance == null) {
            instance = new PurchaseCollector();
        }
        return instance;
    }

    public void startNewSession(String username) {
        this.currentUser = username;
        this.orders.clear();
        this.tickets.clear();
        this.currentCart = null; 
        this.sessionStart = LocalDateTime.now();
    }

    public Cart getCurrentCart() {
        if (currentCart == null) {
            User user = new User(currentUser, 10000.0); 
            currentCart = new Cart(user);
        }
        return currentCart;
    }

    public void setCurrentCart(Cart cart) {
        this.currentCart = cart;
    }

    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    public void addTicket(Ticket ticket) {
        if (ticket != null) {
            tickets.add(ticket);
        }
    }

    public void clearPendingOrders() {
        orders.removeIf(order -> order.getStatus() == Order.OrderStatus.PENDING);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public double getTotalEcommerceValue() {
        return orders.stream()
            .filter(order -> order.getStatus() == Order.OrderStatus.CONFIRMED)
            .mapToDouble(Order::getTotal).sum();
    }

    public double getTotalCartValue() {
        return orders.stream()
            .filter(order -> order.getStatus() == Order.OrderStatus.PENDING)
            .mapToDouble(Order::getTotal).sum();
    }

    public double getTotalTicketValue() {
        return tickets.stream().mapToDouble(Ticket::getPricePaid).sum();
    }

    public double getTotalWorth() {
        return getTotalEcommerceValue() + getTotalTicketValue();
    }

    public int getTotalOrders() {
        return orders.size();
    }

    public int getTotalTickets() {
        return tickets.size();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = (width - text.length()) / 2;
        return repeatChar(' ', padding) + text + repeatChar(' ', width - text.length() - padding);
    }

    private String repeatChar(char ch, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public void displayPurchaseSummary() {
        final int BOX_WIDTH = 66;

        System.out.println("\n╔" + repeatChar('═', BOX_WIDTH - 2) + "╗");
        System.out.println("║" + centerText("PURCHASE SUMMARY", BOX_WIDTH - 2) + "║");
        System.out.println("╠" + repeatChar('═', BOX_WIDTH - 2) + "╣");

        String sessionUser = (currentUser != null ? currentUser : "Guest");
        String sessionStart = (this.sessionStart != null ?
            this.sessionStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "N/A");

        System.out.printf("║ Session User: %-" + (BOX_WIDTH - 16) + "s ║%n", sessionUser);
        System.out.printf("║ Session Start: %-" + (BOX_WIDTH - 17) + "s ║%n", sessionStart);
        System.out.println("╚" + repeatChar('═', BOX_WIDTH - 2) + "╝");

        // Confirmed E-commerce Orders Section
        List<Order> confirmedOrders = orders.stream()
            .filter(order -> order.getStatus() == Order.OrderStatus.CONFIRMED)
            .toList();

        if (!confirmedOrders.isEmpty()) {
            System.out.println("\n📦 CONFIRMED E-COMMERCE ORDERS:");
            printAlignedTable(
                List.of("#", "Order ID", "Items", "Total"),
                confirmedOrders.stream().map(order -> List.of(
                    String.valueOf(confirmedOrders.indexOf(order) + 1),
                    "Order #" + order.getOrderId(),
                    String.valueOf(order.getItems().values().stream().mapToInt(Integer::intValue).sum()),
                    CurrencyUtils.formatPrice(order.getTotal())
                )).toList(),
                List.of(4, 16, 8, 12)
            );
        }

        double pendingValue = getTotalCartValue();
        if (pendingValue > 0) {
            System.out.println("\n🛒 CURRENT CART:");
            printAlignedTable(
                List.of("Description", "Amount"),
                List.of(List.of("Cart Total", CurrencyUtils.formatPrice(pendingValue))),
                List.of(37, 13)
            );
        }

        if (!tickets.isEmpty()) {
            System.out.println("\n✈️  FLIGHT TICKETS:");
            printAlignedTable(
                List.of("#", "Flight", "Route", "Price"),
                tickets.stream().map(ticket -> List.of(
                    String.valueOf(tickets.indexOf(ticket) + 1),
                    ticket.getFlight().getFlightId(),
                    ticket.getFlight().getOrigin() + "-" + ticket.getFlight().getDestination(),
                    CurrencyUtils.formatPrice(ticket.getPricePaid())
                )).toList(),
                List.of(4, 16, 20, 12)
            );
        }

        System.out.println("\n💰 FINANCIAL SUMMARY:");
        printAlignedTable(
            List.of("Description", "Amount"),
            List.of(
                List.of("Confirmed E-commerce Purchases", CurrencyUtils.formatPrice(getTotalEcommerceValue())),
                List.of("Flight Tickets", CurrencyUtils.formatPrice(getTotalTicketValue())),
                List.of("TOTAL PAID", CurrencyUtils.formatPrice(getTotalWorth()))
            ),
            List.of(37, 13)
        );

        if (confirmedOrders.isEmpty() && tickets.isEmpty() && pendingValue == 0) {
            System.out.println("\n📭 No purchases recorded in this session yet.");
            System.out.println("   Start shopping or booking flights to see your summary!");
        }
    }


    private void printAlignedTable(List<String> headers, List<List<String>> rows, List<Integer> columnWidths) {
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
                if (value.matches(".*₹.*") || value.matches("\\d+")) {
                    System.out.printf(" %" + (width - 2) + "s │", value);
                } else {
                    System.out.printf(" %-" + (width - 2) + "s │", value);
                }
            }
            System.out.println();
        }

        System.out.println(bottomBorder);
    }
}
