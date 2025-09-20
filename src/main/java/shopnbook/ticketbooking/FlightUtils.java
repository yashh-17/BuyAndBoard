package shopnbook.ticketbooking;

import java.util.Map;

public class FlightUtils {

    /**
     * Books a seat on the given flight if available.
     * @param event the flight
     * @param seatNo seat number (e.g., "S5")
     * @return true if booking is successful, false otherwise
     */
    public static boolean bookSeat(Event event, String seatNo) {
        Map<String, Boolean> seatMap = event.getSeatMap();
        if (seatMap.containsKey(seatNo) && !seatMap.get(seatNo)) {
            seatMap.put(seatNo, true);
            return true;
        }
        return false; // invalid seat or already booked
    }

    /**
     * Applies discount to the base price.
     * @param basePrice original ticket price
     * @param discountPercentage discount percentage (e.g., 10 for 10%)
     * @return final discounted price
     */
    public static double applyDiscount(double basePrice, double discountPercentage) {
        if (discountPercentage <= 0) return basePrice;
        return basePrice - (basePrice * discountPercentage / 100);
    }

    /**
     * Validates passenger name input.
     * @param name passenger name
     * @return true if valid, false otherwise
     */
    public static boolean validatePassengerName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Validates seat number.
     * @param event the flight
     * @param seatNo seat number
     * @return true if valid and exists in seatMap
     */
    public static boolean validateSeat(Event event, String seatNo) {
        return event.getSeatMap().containsKey(seatNo);
    }

    /**
     * Pretty print flight information.
     * @param event flight event
     */
    public static void printFlightInfo(Event event) {
        System.out.printf(
                "Flight: %s | Airline: %s | %s → %s | Departure: %s | Duration: %s | Price: ₹%.2f | Seats Available: %d%n",
                event.getFlightId(),
                event.getAirline(),
                event.getOrigin(),
                event.getDestination(),
                event.getDepartureTime(),
                event.getDuration(),
                event.getBasePrice(),
                event.getAvailableSeats()
        );
    }
}
