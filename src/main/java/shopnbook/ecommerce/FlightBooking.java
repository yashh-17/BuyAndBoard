package shopnbook.ecommerce;

import shopnbook.ticketbooking.Event;

public class FlightBooking {
    private Event flight;
    private String passenger;
    private String seat;
    private double price;

    public FlightBooking(Event flight, String passenger, String seat, double price) {
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.price = price;
    }

    public Event getFlight() {
        return flight;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "FlightBooking{" +
                "flight=" + flight.getFlightId() +
                ", passenger='" + passenger + '\'' +
                ", seat='" + seat + '\'' +
                ", price=" + price +
                '}';
    }
}
