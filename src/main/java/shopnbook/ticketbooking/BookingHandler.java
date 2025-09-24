package shopnbook.ticketbooking;

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
 // Generate and print ticket
    public void generateAndPrintTicket(Event flight, String passengerName, String seat, double price) {
        if (flight == null) return;

        Ticket ticket = new Ticket(flight, passengerName, seat, price);

        System.out.println("\n🎟 TICKET DETAILS 🎟");
        System.out.println(ticket);
    }


    // ==============================
    // 🚀 New Round-Trip Booking Logic
    // ==============================
    // ==============================
// 🚀 Fixed Round-Trip Booking Logic
// ==============================
public void handleBooking() {
    // Menu for journey type
    System.out.println("\nSelect Journey Type:");
    System.out.println("1. Single Way");
    System.out.println("2. Round Trip");

    int choice = -1;
    while (choice != 1 && choice != 2) {
        System.out.print("Enter choice (1/2): ");
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
            if (choice != 1 && choice != 2) {
                System.out.println("❌ Please enter 1 or 2.");
            }
        } else {
            System.out.println("❌ Invalid input. Please enter a number.");
            sc.nextLine(); // clear invalid input
        }
    }
    isRoundTrip = (choice == 2);

    String passenger = takePassengerInput();

    // Onward journey
    System.out.print("Enter Origin: ");
    String origin = sc.nextLine();
    System.out.print("Enter Destination: ");
    String destination = sc.nextLine();

    List<Event> onwardFlights = filterFlights(origin, destination);
    Event onwardFlight = selectFlightAndSeat(onwardFlights);
    double onwardPrice = bookFlight(onwardFlight, passenger);

    // Save onward seat before it gets overwritten
    String onwardSeat = lastBookedSeat;

    // Return journey if round-trip
    if (isRoundTrip) {
        List<Event> returnFlights = filterFlights(destination, origin);
        System.out.println("\n🔄 Select Return Flight");
        Event returnFlight = selectFlightAndSeat(returnFlights);
        double returnPrice = bookFlight(returnFlight, passenger);

        // Save return seat separately
        String returnSeat = lastBookedSeat;

        // Store tickets in a list
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(onwardFlight, passenger, onwardSeat, onwardPrice));
        tickets.add(new Ticket(returnFlight, passenger, returnSeat, returnPrice));

        // Print all tickets
        System.out.println("\n🎫 ROUND-TRIP TICKETS 🎫");
        for (Ticket t : tickets) {
            System.out.println(t);
        }
    } else {
        // One-way booking (pass lastBookedSeat too)
        generateAndPrintTicket(onwardFlight, passenger, onwardSeat, onwardPrice);
    }
}

}
