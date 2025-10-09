package shopnbook.ticketbooking;

import shopnbook.utils.CurrencyUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Ticket {
    private String ticketId;
    private String passengerName;
    private String seatNumber;
    private double pricePaid;
    private LocalDateTime bookingTime;
    private Event flight;

    public Ticket(Event flight, String passengerName, String seatNumber, double pricePaid) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8);
        this.flight = flight;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
        this.pricePaid = pricePaid;
        this.bookingTime = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public String getPassengerName() { return passengerName; }
    public String getSeatNumber() { return seatNumber; }
    public double getPricePaid() { return pricePaid; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public Event getFlight() { return flight; }

    @Override
    public String toString() {
        return String.format(
                "Ticket ID: %s\nPassenger: %s\nFlight: %s (%s → %s)\nSeat: %s\nPrice: %s\nBooked At: %s\n",
                ticketId,
                passengerName,
                flight.getFlightId(),
                flight.getOrigin(),
                flight.getDestination(),
                seatNumber,
                CurrencyUtils.formatPrice(pricePaid),
                bookingTime
        );
    }

    public static void printRoundTrip(List<Ticket> tickets) {
        if (tickets == null || tickets.size() < 2) {
            System.out.println("⚠ Not enough tickets for round-trip printing.");
            return;
        }

        System.out.println("\n🎫 ROUND-TRIP SUMMARY 🎫");
        System.out.println("----------------------------");

        String passenger = tickets.get(0).getPassengerName();
        System.out.printf("Passenger: %s\n", passenger);

        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);

            if (i == 0) {
                System.out.printf("Onward Flight: %s (%s → %s), Seat %s, Price %s\n",
                        t.getFlight().getFlightId(),
                        t.getFlight().getOrigin(),
                        t.getFlight().getDestination(),
                        t.getSeatNumber(),
                        CurrencyUtils.formatPrice(t.getPricePaid()));
            } else if (i == tickets.size() - 1) {
                System.out.printf("Return Flight: %s (%s → %s), Seat %s, Price %s\n",
                        t.getFlight().getFlightId(),
                        t.getFlight().getOrigin(),
                        t.getFlight().getDestination(),
                        t.getSeatNumber(),
                        CurrencyUtils.formatPrice(t.getPricePaid()));
            } else {
                System.out.printf("Layover Flight %d: %s (%s → %s), Seat %s, Price %s\n",
                        i,
                        t.getFlight().getFlightId(),
                        t.getFlight().getOrigin(),
                        t.getFlight().getDestination(),
                        t.getSeatNumber(),
                        CurrencyUtils.formatPrice(t.getPricePaid()));
            }
        }

        double total = tickets.stream().mapToDouble(Ticket::getPricePaid).sum();
        System.out.printf("Total Price: %s\n", CurrencyUtils.formatPrice(total));
        System.out.println("----------------------------");
    }
}
