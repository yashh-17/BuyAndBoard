package shopnbook;

import shopnbook.ecommerce.EcommerceApp;
import shopnbook.ticketbooking.TicketBookingApp;
import shopnbook.game.*;

import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to BuyAndBoard!");
        System.out.println("1. E-Commerce Shopping");
        System.out.println("2. Ticket Booking");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
        int choice = sc.nextInt();

        switch(choice) {
            case 1: 
                EcommerceApp.start();
                break;
            
            case 2 : 
            TicketBookingApp.start();
            break;

            case 0 : 
            System.out.println("Exiting...");
            break;

            default : 
            System.out.println("Invalid choice");
            break;

        }
    }
}
