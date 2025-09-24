package shopnbook.ticketbooking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Model class representing a flight
public class Event {
    private String flightId;
    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private String duration;  // e.g., "2h 30m"
    private double basePrice;
    private int availableSeats;
    private Map<String, Boolean> seatMap; // Seat -> true if booked

    // Field to store the last booked seat for this flight
    private String lastBookedSeat;

    // Constructor to initialize fields and seat map
    public Event(String flightId, String airline, String origin, String destination,
                 LocalDateTime departureTime, String duration,
                 double basePrice, int totalSeats) {
        this.flightId = flightId;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.duration = duration;
        this.basePrice = basePrice;
        this.availableSeats = totalSeats;

        // Initialize seat map (false = free, true = booked)
        this.seatMap = new HashMap<>();
        for (int i = 1; i <= totalSeats; i++) {
            seatMap.put("S" + i, false); // seats labeled S1, S2, ...
        }
    }

    // Getters and setters
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public int getRemainingSeats() { return availableSeats; }
    public Map<String, Boolean> getSeatMap() { return seatMap; }

    // Last booked seat getters/setters
    public void setLastBookedSeat(String seat) {
        this.lastBookedSeat = seat;
    }

    public String getLastBookedSeat() {
        return lastBookedSeat;
    }

    // Book a seat
    public boolean bookSeat(String seatNumber) {
        if (!seatMap.containsKey(seatNumber)) {
            System.out.println("⚠ Invalid seat number: " + seatNumber);
            return false;
        }
        if (seatMap.get(seatNumber)) {
            System.out.println("⚠ Seat " + seatNumber + " is already booked.");
            return false;
        }
        seatMap.put(seatNumber, true);
        availableSeats--;
        setLastBookedSeat(seatNumber); // store booked seat
        return true;
    }

    // Return list of available seat numbers
    public List<String> getAvailableSeats() {
        List<String> available = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : seatMap.entrySet()) {
            if (!entry.getValue()) {
                available.add(entry.getKey().toUpperCase());
            }
        }
        return available;
    }

    // Check if a seat is available
    public boolean isSeatAvailable(String seatNumber) {
        return seatMap.containsKey(seatNumber) && !seatMap.get(seatNumber);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        return String.format("[%s] %s → %s | %s | %s | ₹%.2f | Seats left: %d",
                flightId, origin, destination,
                departureTime.format(fmt), airline, basePrice, availableSeats);
    }
}
