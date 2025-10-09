package shopnbook.auth;

import shopnbook.utils.PurchaseCollector;
import java.util.Scanner;

public class AuthController {
    public static boolean requireAuth(Scanner sc) {
        System.out.println("1. Login\n2. Signup");
        System.out.print("Select option: ");
        int authChoice = sc.nextInt();
        sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        boolean authenticated = false;
        if (authChoice == 1) {
            authenticated = AuthService.login(username, password);
            if (!authenticated) {
                System.out.println("Login failed. Exiting...");
                return false;
            }
        } else if (authChoice == 2) {
            boolean signedUp = AuthService.signup(username, password);
            if (!signedUp) {
                System.out.println("Signup failed (username exists). Exiting...");
                return false;
            }
            System.out.println("Signup successful. Please login now.");
            System.out.print("Username: ");
            String loginUser = sc.nextLine();
            System.out.print("Password: ");
            String loginPass = sc.nextLine();
            authenticated = AuthService.login(loginUser, loginPass);
            if (!authenticated) {
                System.out.println("Login failed. Exiting...");
                return false;
            }
        } else {
            System.out.println("Invalid choice. Exiting...");
            return false;
        }

        PurchaseCollector.getInstance().startNewSession(username);
        System.out.println("🛒 Session started for " + username);

        return true;
    }
}


