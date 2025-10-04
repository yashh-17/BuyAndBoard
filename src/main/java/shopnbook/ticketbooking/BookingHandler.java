package shopnbook.ticketbooking;

import shopnbook.utils.PurchaseCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main booking logic controller
public class BookingHandler {
    private List<Event> flights; // List of all available flights
    private Scanner sc;

    // Store the last booked seat
    private String lastBookedSeat;

    // Round-trip flag
    private boolean isRoundTrip;

    public BookingHandler(List<Event> flights) {
        this.flights = flights;
        this.sc = new Scanner(System.in);
        this.isRoundTrip = false; // default
    }

    // Display flights
    public void displayFlights() {
        System.out.println("\n✈ AVAILABLE FLIGHTS ✈");
        for (Event f : flights) {
            System.out.println(f);
        }
    }

    // Filter flights by origin and destination
    public List<Event> filterFlights(String origin, String destination) {
        List<Event> result = new ArrayList<>();
        for (Event f : flights) {
            if (f.getOrigin().equalsIgnoreCase(origin) &&
                f.getDestination().equalsIgnoreCase(destination)) {
                result.add(f);
            }
        }
        return result;
    }

    // Take passenger input (just name for now)
    public String takePassengerInput() {
        System.out.println("\nEnter Passenger Name:");
        return sc.nextLine();
    }

    // Select flight and seat
    public Event selectFlightAndSeat(List<Event> availableFlights) {
        if (availableFlights.isEmpty()) {
            System.out.println("⚠ No flights available for given filter.");
            return null;
        }

        // Display flights with index
        System.out.println("\nSelect Flight by Index:");
        for (int i = 0; i < availableFlights.size(); i++) {
            System.out.println(i + ". " + availableFlights.get(i));
        }

        int choice = -1;
        while (true) {
            System.out.print("Enter flight index: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
                if (choice >= 0 && choice < availableFlights.size()) {
                    break;
                } else {
                    System.out.println("❌ Invalid flight index. Try again.");
                }
            } else {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine(); // discard invalid input
            }
        }

        Event chosen = availableFlights.get(choice);

        // Show available seats in sorted order
        List<String> availableSeats = chosen.getAvailableSeats();
        if (availableSeats.isEmpty()) {
            System.out.println("⚠ No seats left on this flight!");
            return null;
        }
        availableSeats.sort(null); // sort alphabetically
        System.out.println("\nAvailable Seats: " + String.join(", ", availableSeats));

        // Prompt user to select a seat
        String seat = "";
        while (true) {
            System.out.print("Enter Seat Number to Book: ");
            seat = sc.nextLine().trim().toUpperCase(); // normalize input
            if (availableSeats.contains(seat)) {
                break; // valid seat
            } else {
                System.out.println("❌ Invalid seat. Please choose from available seats.");
            }
        }

        // Book the seat
        chosen.bookSeat(seat);
        System.out.println("✅ Seat " + seat + " booked on flight " + chosen.getFlightId());

        // Store the seat internally to use in ticket generation
        lastBookedSeat = seat;

        return chosen;
    }

    // Direct booking without discount
    public double bookFlight(Event chosen, String passengerName) {
        if (chosen == null) return 0;

        double price = chosen.getBasePrice();
        System.out.println("💰 Ticket Price: " + price);
        return price;
    }

    // Generate and print ticket
    public void generateAndPrintTicket(Event flight, String passengerName, String seat, double price) {
        if (flight == null) return;

        Ticket ticket = new Ticket(flight, passengerName, seat, price);

        // Report ticket to purchase collector
        PurchaseCollector.getInstance().addTicket(ticket);

        System.out.println("\n🎟 TICKET DETAILS 🎟");
        System.out.println(ticket);
    }

    // Get last booked seat
    public String getLastBookedSeat() {
        return lastBookedSeat;
    }
}