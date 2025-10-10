package shopnbook.ticketbooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import shopnbook.ecommerce.Cart;
import shopnbook.utils.CurrencyUtils;
import shopnbook.utils.PurchaseCollector;

public class TicketBookingApp {

    public static void start(String currentUsername) {
        System.out.println("Welcome to Ticket Booking!");

        Cart cart = PurchaseCollector.getInstance().getCurrentCart();
        Scanner sc = new Scanner(System.in);

        List<Event> flights = new ArrayList<>();
        flights.add(new Event("AI100", "Air India", "Vizag", "Gannavaram",
                LocalDateTime.of(2025, 9, 20, 6, 0), "1h 15m", 2500.0, 100));
        flights.add(new Event("6E200", "IndiGo", "Vizag", "Delhi",
                LocalDateTime.of(2025, 9, 20, 7, 30), "3h 25m", 5500.0, 100));
        flights.add(new Event("SG200", "SpiceJet", "Gannavaram", "Vizag",
                LocalDateTime.of(2025, 9, 20, 9, 0), "1h 10m", 2300.0, 100));
        flights.add(new Event("SG201", "SpiceJet", "Vizag", "Gannavaram",
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
        flights.add(new Event("AI313", "Air India", "Delhi", "Mumbai",
                LocalDateTime.of(2025, 10, 2, 6, 0), "2h 10m", 4300.0, 150));
        flights.add(new Event("6E414", "IndiGo", "Bangalore", "Mumbai",
                LocalDateTime.of(2025, 10, 3, 20, 0), "2h 25m", 4600.0, 100));
        flights.add(new Event("SG515", "SpiceJet", "Mumbai", "Bangalore",
                LocalDateTime.of(2025, 10, 4, 15, 0), "2h 30m", 4800.0, 100));
                flights.add(new Event("AI616", "Air India", "Chennai", "Delhi",
                LocalDateTime.of(2025, 10, 5, 9, 0), "2h 55m", 5200.0, 150));
        flights.add(new Event("6E717", "IndiGo", "Delhi", "Chennai",
                LocalDateTime.of(2025, 10, 6, 14, 30), "2h 50m", 5000.0, 100));
        flights.add(new Event("SG818", "SpiceJet", "Vizag", "Mumbai",
                LocalDateTime.of(2025, 10, 7, 8, 0), "3h 15m", 5500.0, 100));
        flights.add(new Event("AI919", "Air India", "Mumbai", "Vizag",
                LocalDateTime.of(2025, 10, 8, 18, 0), "3h 10m", 5300.0, 100));
                flights.add(new Event("6E020", "IndiGo", "Chennai", "Bangalore",
                LocalDateTime.of(2025, 10, 9, 12, 0), "1h 30m", 3500.0, 100));
        flights.add(new Event("SG121", "SpiceJet", "Bangalore", "Chennai",
                LocalDateTime.of(2025, 10, 10, 16, 0), "1h 25m", 3300.0, 100));
        flights.add(new Event("AI222", "Air India", "Hyderabad", "Bangolare",
                LocalDateTime.of(2025, 10, 11, 7, 0), "1h 15m", 3400.0, 100));
        flights.add(new Event("6E323", "IndiGo", "Bangalore", "Hyderabad",
                LocalDateTime.of(2025, 10, 12, 19, 0), "1h 5m", 3200.0, 100));
                flights.add(new Event("SG424", "SpiceJet", "Hyderabad", "Delhi",
                LocalDateTime.of(2025, 10, 13, 13, 0), "2h 30m", 4800.0, 100));
        flights.add(new Event("AI525", "Air India", "Delhi", "Hyderabad",
                LocalDateTime.of(2025, 10, 14, 10, 0), "2h 25m", 4600.0, 100));
        flights.add(new Event("AI525", "Air India", "Vizag", "Hyderabad",
                LocalDateTime.of(2025, 10, 14, 10, 0), "1h 25m", 4600.0, 100));
        flights.add(new Event("AI525", "Indigo", "Hyderabad", "Vizag",
                LocalDateTime.of(2025, 10, 14, 10, 0), "2h 15m", 4600.0, 100));
                flights.add(new Event("SG626", "SpiceJet", "Hyderabad", "Bangalore",
                LocalDateTime.of(2025, 10, 15, 17, 0), "1h 5m", 3200.0, 100));
        flights.add(new Event("6E727", "IndiGo", "Bangalore", "Hyderabad",
                LocalDateTime.of(2025, 10, 16, 9, 0), "1h 15m", 3400.0, 100));
        flights.add(new Event("SG828", "SpiceJet", "Hyderabad", "Gannavaram",
                LocalDateTime.of(2025, 10, 17, 15, 0), "1h 10m", 2300.0, 100));
        flights.add(new Event("AI929", "Air India", "Gannavaram", "Hyderabad",
                LocalDateTime.of(2025, 10, 18, 11, 0), "1h 15m", 2500.0, 100));
        // 1. Display available flights
        
                

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
        double onwardPrice = handler.bookFlight(onwardFlight, passengerName);

        // Use grouped seat selection with case-insensitive handling; this books the seat and decrements availability
        String selectedSeat = handler.selectSeatForFlight(onwardFlight);
        if (selectedSeat != null) {
            cart.addFlightBooking(onwardFlight, passengerName, selectedSeat, onwardPrice);
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
            if (returnFlight != null) {
                String returnSeat = handler.getLastBookedSeat();
                if (returnSeat != null) {
                    cart.addFlightBooking(returnFlight, passengerName, returnSeat, returnPrice);
                }
            }
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

    // Pagination-based seat selection method removed in favor of grouped seat selection in BookingHandler
}
