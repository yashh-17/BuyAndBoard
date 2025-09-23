package shopnbook.ticketbooking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Model class for a booked ticket
public class Ticket {
    private String ticketId;
    private String passengerName;
    private String seatNumber;
    private double pricePaid;  // keep field name same to avoid breaking other code
    private LocalDateTime bookingTime;
    private Event flight;

    // Constructor
    public Ticket(Event flight, String passengerName, String seatNumber, double pricePaid) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8); // short unique ID
        this.flight = flight;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
        this.pricePaid = pricePaid;
        this.bookingTime = LocalDateTime.now();
    }

    // Getters
    public String getTicketId() { return ticketId; }
    public String getPassengerName() { return passengerName; }
    public String getSeatNumber() { return seatNumber; }
    public double getPricePaid() { return pricePaid; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public Event getFlight() { return flight; }

    @Override
    public String toString() {
        return String.format(
                "Ticket ID: %s\nPassenger: %s\nFlight: %s (%s → %s)\nSeat: %s\nPrice: ₹%.2f\nBooked At: %s\n",
                ticketId,
                passengerName,
                flight.getFlightId(),
                flight.getOrigin(),
                flight.getDestination(),
                seatNumber,
                pricePaid,
                bookingTime
        );
    }

    // ==============================
    // 🚀 New Helper for Round-Trip
    // ==============================
    public static void printRoundTrip(List<Ticket> tickets) {
        if (tickets == null || tickets.size() < 2) {
            System.out.println("⚠ Not enough tickets for round-trip printing.");
            return;
        }

        System.out.println("\n🎫 ROUND-TRIP SUMMARY 🎫");
        System.out.println("----------------------------");

        Ticket onward = tickets.get(0);
        Ticket returning = tickets.get(1);

        System.out.printf("Passenger: %s\n", onward.getPassengerName());
        System.out.printf("Onward Flight: %s (%s → %s), Seat %s, Price ₹%.2f\n",
                onward.getFlight().getFlightId(),
                onward.getFlight().getOrigin(),
                onward.getFlight().getDestination(),
                onward.getSeatNumber(),
                onward.getPricePaid());

        System.out.printf("Return Flight: %s (%s → %s), Seat %s, Price ₹%.2f\n",
                returning.getFlight().getFlightId(),
                returning.getFlight().getOrigin(),
                returning.getFlight().getDestination(),
                returning.getSeatNumber(),
                returning.getPricePaid());

        double total = onward.getPricePaid() + returning.getPricePaid();
        System.out.printf("Total Price: ₹%.2f\n", total);

        System.out.println("----------------------------");
    }
}