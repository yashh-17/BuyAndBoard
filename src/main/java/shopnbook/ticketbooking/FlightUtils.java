package shopnbook.ticketbooking;

import java.util.Map;

public class FlightUtils {

    public static boolean bookSeat(Event event, String seatNo) {
        Map<String, Boolean> seatMap = event.getSeatMap();
        if (seatMap.containsKey(seatNo) && !seatMap.get(seatNo)) {
            seatMap.put(seatNo, true);
            return true;
        }
        return false;
    }

    public static double applyDiscount(double basePrice, double discountPercentage) {
        if (discountPercentage <= 0) return basePrice;
        return basePrice - (basePrice * discountPercentage / 100);
    }

    public static boolean validatePassengerName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean validateSeat(Event event, String seatNo) {
        return event.getSeatMap().containsKey(seatNo);
    }

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
                event.getRemainingSeats()
        );
    }
}
