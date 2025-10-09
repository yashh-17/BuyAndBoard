package shopnbook.ticketbooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import shopnbook.ecommerce.EcommerceApp;
import shopnbook.ecommerce.Cart;
import shopnbook.ecommerce.User;
import shopnbook.utils.CurrencyUtils;
import shopnbook.utils.PurchaseCollector;

public class TicketBookingApp {

    public static void start(String currentUsername) {
        System.out.println("Welcome to Ticket Booking!");

        Cart cart = PurchaseCollector.getInstance().getCurrentCart();
        Scanner sc = new Scanner(System.in);

        List<Event> flights = new ArrayList<>();
        flights.add(new Event("AI100", "Air India", "Vizag", "Ganavaram",
                LocalDateTime.of(2025, 9, 20, 6, 0), "1h 15m", 2500.0, 100));
        flights.add(new Event("6E200", "IndiGo", "Vizag", "Delhi",
                LocalDateTime.of(2025, 9, 20, 7, 30), "3h 25m", 5500.0, 100));
        flights.add(new Event("SG200", "SpiceJet", "Ganavaram", "Vizag",
                LocalDateTime.of(2025, 9, 20, 9, 0), "1h 10m", 2300.0, 100));
        flights.add(new Event("SG201", "SpiceJet", "Vizag", "Ganavaram",
                LocalDateTime.of(2025, 9, 20, 11, 0), "1h 15m", 2500.0, 100));
        flights.add(new Event("AI101", "Air India", "Delhi", "Mumbai",
                LocalDateTime.of(2025, 9, 20, 10, 30), "2h 15m", 4500.0, 100));
        flights.add(new Event("6E102", "IndiGo", "Mumbai", "Delhi",
                LocalDateTime.of(2025, 9, 20, 15, 0), "2h 10m", 4300.0, 100));
        flights.add(new Event("6E202", "IndiGo", "Delhi", "Bangalore",
                LocalDateTime.of(2025, 9, 21, 8, 15), "2h 45m", 4200.0, 100));
        flights.add(new Event("SG303", "SpiceJet", "Mumbai", "Chennai",
                LocalDateTime.of(2025, 9, 22, 14, 0), "2h 30m", 4800.0, 100));
        flights.add(new Event("AI404", "Air India", "Bangalore", "Delhi",
                LocalDateTime.of(2025, 9, 23, 16, 45), "2h 50m", 5000.0, 200));
        flights.add(new Event("6E505", "IndiGo", "Chennai", "Mumbai",
                LocalDateTime.of(2025, 9, 24, 12, 0), "2h 20m", 4600.0, 250));
        flights.add(new Event("SG606", "SpiceJet", "Delhi", "Mumbai",
                LocalDateTime.of(2025, 9, 25, 9, 30), "2h 15m", 4400.0, 300));
        flights.add(new Event("AI707", "Air India", "Mumbai", "Bangalore",
                LocalDateTime.of(2025, 9, 26, 18, 0), "2h 35m", 4700.0, 200));
        flights.add(new Event("6E808", "IndiGo", "Bangalore", "Chennai",
                LocalDateTime.of(2025, 9, 27, 11, 15), "1h 30m", 3500.0, 100));
        flights.add(new Event("SG909", "SpiceJet", "Chennai", "Delhi",
                LocalDateTime.of(2025, 9, 28, 13, 45), "2h 55m", 5200.0, 150));
        flights.add(new Event("AI010", "Air India", "Vizag", "Delhi",
                LocalDateTime.of(2025, 9, 29, 7, 0), "3h 30m", 6000.0, 100));
        flights.add(new Event("6E111", "IndiGo", "Delhi", "Vizag",
                LocalDateTime.of(2025, 9, 30, 19, 0), "3h 20m", 5800.0, 100));
        flights.add(new Event("SG212", "SpiceJet", "Mumbai", "Delhi",
                LocalDateTime.of(2025, 10, 1, 17, 30), "2h 15m", 4500.0, 200));
        // 2. Create booking handler
        BookingHandler handler = new BookingHandler(flights);

        // 3. Select journey type
        System.out.println("\nSelect Journey Type:");
        System.out.println("1. One-way");
        System.out.println("2. Round-trip");
        int journeyChoice = -1;
        while (journeyChoice != 1 && journeyChoice != 2) {
            System.out.print("Enter choice (1/2): ");
            if (sc.hasNextInt()) {
                journeyChoice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
            }
        }
        boolean isRoundTrip = (journeyChoice == 2);

        System.out.print("Enter Passenger Name: ");
        String passengerName = sc.nextLine().trim();
        System.out.print("Enter Origin: ");
        String origin = sc.nextLine().trim();
        System.out.print("Enter Destination: ");
        String destination = sc.nextLine().trim();

        List<Event> onwardFlights = handler.filterFlights(origin, destination);
        if (onwardFlights.isEmpty()) {
            System.out.println("❌ No flights available for this route.");
            return;
        }

        System.out.println("Select Flight by Index:");
        for (int i = 0; i < onwardFlights.size(); i++) {
            Event f = onwardFlights.get(i);
            System.out.println(i + ". " + f.toString());
        }

        System.out.print("Enter flight index: ");
        int flightIndex = sc.nextInt();
        sc.nextLine();
        if (flightIndex < 0 || flightIndex >= onwardFlights.size()) {
            System.out.println("❌ Invalid flight selection.");
            return;
        }

        Event onwardFlight = onwardFlights.get(flightIndex);
        String selectedFlightId = onwardFlight.getFlightId();
        double onwardPrice = handler.bookFlight(onwardFlight, passengerName);

        List<String> availableSeats = Arrays.asList("S1", "S10", "S100", "S91", "S92", "S93", "S94", "S95", "S96", "S97", "S98", "S99");

        // Sort seats numerically
        Collections.sort(availableSeats, (a, b) -> {
            int numA = Integer.parseInt(a.substring(1));
            int numB = Integer.parseInt(b.substring(1));
            return Integer.compare(numA, numB);
        });

        System.out.println("Available Seats (sorted):");
        String selectedSeat = selectSeatWithPagination(availableSeats, sc);

        if (selectedSeat != null && availableSeats.contains(selectedSeat)) {
            onwardFlight.bookSeat(selectedSeat);
            cart.addFlightBooking(onwardFlight, passengerName, selectedSeat, onwardPrice);
            System.out.println("✅ Seat " + selectedSeat + " booked on flight " + selectedFlightId);
            System.out.println("💰 Ticket Price: " + onwardFlight.getPrice());
        } else {
            System.out.println("❌ Invalid seat selection or no seat booked.");
        }

        Event returnFlight = null;
        double returnPrice = 0;
        if (isRoundTrip) {
            List<Event> returnFlights = handler.filterFlights(destination, origin);
            if (returnFlights.isEmpty()) {
                System.out.println("❌ No return flights available.");
                return;
            }
            returnFlight = handler.selectFlightAndSeat(returnFlights);
            returnPrice = handler.bookFlight(returnFlight, passengerName);
        }

        double totalAmount = onwardPrice + returnPrice;

        System.out.println("\n💰 FLIGHT BOOKING SUMMARY");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Onward Flight: " + onwardFlight.getFlightId() + " (" + origin + " → " + destination + ")");
        System.out.println("Price: " + CurrencyUtils.formatPrice(onwardPrice));
        if (isRoundTrip && returnFlight != null) {
            System.out.println("Return Flight: " + returnFlight.getFlightId() + " (" + destination + " → " + origin + ")");
            System.out.println("Price: " + CurrencyUtils.formatPrice(returnPrice));
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Total Amount: " + CurrencyUtils.formatPrice(totalAmount));

        // Note: Flights were already added to cart immediately after seat selection.
        // Avoid adding them again to prevent duplicates.

        System.out.println("\n✅ FLIGHTS ADDED TO CART!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💰 Total in Cart: " + CurrencyUtils.formatPrice(totalAmount));
        System.out.println("📋 You can now 'Place Order' from the main menu to pay for everything!");
    }

    private static String selectSeatWithPagination(List<String> seats, Scanner sc) {
        final int SEATS_PER_PAGE = 10;
        int page = 0;
        while (page * SEATS_PER_PAGE < seats.size()) {
            int start = page * SEATS_PER_PAGE;
            int end = Math.min(start + SEATS_PER_PAGE, seats.size());
            System.out.println("\n--- Page " + (page + 1) + " ---");
            for (int i = start; i < end; i++) {
                System.out.print(seats.get(i) + " ");
            }
            System.out.println();
            if (end < seats.size()) {
                System.out.print("Enter seat or 'N' for next page: ");
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("N")) {
                    page++;
                } else if (seats.contains(input)) {
                    return input;
                } else {
                    System.out.println("❌ Invalid seat. Try again or 'N' for next page.");
                }
            } else {
                System.out.print("Enter seat: ");
                String input = sc.nextLine().trim();
                if (seats.contains(input)) {
                    return input;
                } else {
                    System.out.println("❌ Invalid seat.");
                    return null;
                }
            }
        }
        return null;
    }
}
