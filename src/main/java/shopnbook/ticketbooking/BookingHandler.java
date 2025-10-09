package shopnbook.ticketbooking;

import shopnbook.utils.PurchaseCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingHandler {
    private List<Event> flights;
    private Scanner sc;
    private String lastBookedSeat;
    private boolean isRoundTrip;

    public BookingHandler(List<Event> flights) {
        this.flights = flights;
        this.sc = new Scanner(System.in);
        this.isRoundTrip = false;
    }

    // Grouped seat display and selection for a chosen flight
    public String selectSeatForFlight(Event chosen) {
        List<String> availableSeats = chosen.getAvailableSeats();
        if (availableSeats.isEmpty()) {
            System.out.println("⚠ No seats left on this flight!");
            return null;
        }
        // Sort numerically and limit to first 15 seats
        availableSeats.sort((a, b) -> Integer.compare(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1))));
        List<String> limited = new ArrayList<>(availableSeats.subList(0, Math.min(15, availableSeats.size())));

        // Group limited seats by type
        List<String> middle = new ArrayList<>();
        List<String> aisle = new ArrayList<>();
        List<String> window = new ArrayList<>();
        for (String s : limited) {
            char t = chosen.getSeatType(s);
            String label = chosen.formatSeatWithType(s);
            if (t == 'M') middle.add(label);
            else if (t == 'A') aisle.add(label);
            else window.add(label);
        }

        // Display grouped lists (only the limited seats)
        if (!middle.isEmpty()) System.out.println("MIDDLE SEATS: " + String.join(" ", middle));
        if (!aisle.isEmpty()) System.out.println("AISLE SEATS: " + String.join(" ", aisle));
        if (!window.isEmpty()) System.out.println("WINDOW SEATS: " + String.join(" ", window));

        String seat = "";
        while (true) {
            System.out.print("Enter Seat Number to Book: ");
            seat = sc.nextLine().trim().toUpperCase();
            if (limited.contains(seat)) {
                break;
            } else {
                System.out.println("❌ Invalid seat. Please choose from the displayed seats (e.g., S3).");
            }
        }

        boolean booked = chosen.bookSeat(seat);
        if (booked) {
            System.out.println("✅ Seat " + seat + " booked on flight " + chosen.getFlightId());
            System.out.println("Remaining available seats: " + chosen.getRemainingSeats());
            lastBookedSeat = seat;
            return seat;
        } else {
            return null;
        }
    }

    public void displayFlights() {
        System.out.println("\n✈ AVAILABLE FLIGHTS ✈");
        for (Event f : flights) {
            System.out.println(f);
        }
    }

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

    public String takePassengerInput() {
        System.out.println("\nEnter Passenger Name:");
        return sc.nextLine();
    }

    public Event selectFlightAndSeat(List<Event> availableFlights) {
        if (availableFlights.isEmpty()) {
            System.out.println("⚠ No flights available for given filter.");
            return null;
        }

        System.out.println("\nSelect Flight by Index:");
        for (int i = 0; i < availableFlights.size(); i++) {
            System.out.println(i + ". " + availableFlights.get(i));
        }

        int choice = -1;
        while (true) {
            System.out.print("Enter flight index: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice < availableFlights.size()) {
                    break;
                } else {
                    System.out.println("❌ Invalid flight index. Try again.");
                }
            } else {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        Event chosen = availableFlights.get(choice);
        String seat = selectSeatForFlight(chosen);
        if (seat == null) {
            return null;
        }
        return chosen;
    }

    public double bookFlight(Event chosen, String passengerName) {
        if (chosen == null) return 0;
        double price = chosen.getBasePrice();
        System.out.println("💰 Ticket Price: " + price);
        return price;
    }
    //

    public void generateAndPrintTicket(Event flight, String passengerName, String seat, double price) {
        if (flight == null) return;
        Ticket ticket = new Ticket(flight, passengerName, seat, price);
        PurchaseCollector.getInstance().addTicket(ticket);
        System.out.println("\n🎟 TICKET DETAILS 🎟");
        System.out.println(ticket);
    }

    public String getLastBookedSeat() {
        return lastBookedSeat;
    }
}
