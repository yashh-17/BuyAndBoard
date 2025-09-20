package shopnbook.ticketbooking;

import java.time.LocalDateTime;
import java.util.UUID;

// Model class for a booked ticket
public class Ticket {
    private String ticketId;
    private String passengerName;
    private String seatNumber;
    private double pricePaid;
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
                "Ticket ID: %s\nPassenger: %s\nFlight: %s (%s → %s)\nSeat: %s\nPrice Paid: ₹%.2f\nBooked At: %s\n",
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
}
