package shopnbook.ticketbooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketBookingApp {
    public static void start() {
        System.out.println("Welcome to Ticket Booking!");

        // 1. Create sample flights
        List<Event> flights = new ArrayList<>();
        flights.add(new Event("AI101", "Air India", "Delhi", "Mumbai",
                LocalDateTime.of(2025, 9, 20, 10, 30),
                "2h 15m", 4500.0, 5));
        flights.add(new Event("6E202", "IndiGo", "Delhi", "Bangalore",
                LocalDateTime.of(2025, 9, 21, 8, 15),
                "2h 45m", 4200.0, 5));
        flights.add(new Event("SG303", "SpiceJet", "Mumbai", "Chennai",
                LocalDateTime.of(2025, 9, 22, 14, 0),
                "2h 30m", 4800.0, 5));

        // 2. Create handler with flight list
        BookingHandler handler = new BookingHandler(flights);

        // 3. Show all flights
        handler.displayFlights();

        // 4. Take user input for filtering
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Origin: ");
        String origin = sc.nextLine().trim();
        System.out.print("Enter Destination: ");
        String destination = sc.nextLine().trim();

        // 5. Filter flights based on input
        var available = handler.filterFlights(origin, destination);

        if (available.isEmpty()) {
            System.out.println("❌ No flights found for " + origin + " → " + destination);
            return; // stop flow if no flights
        }

        // 6. Continue booking flow
        String passenger = handler.takePassengerInput();
        Event chosen = handler.selectFlightAndSeat(available); // selects flight and seat internally

        if (chosen == null) {
            System.out.println("❌ Booking failed.");
            return;
        }

        // ✅ Use base price
        double price = chosen.getBasePrice();

        // Generate ticket (seat is automatically taken from lastBookedSeat)
        handler.generateAndPrintTicket(chosen, passenger, price);

        System.out.println("✅ Booking completed!");
    }
}
