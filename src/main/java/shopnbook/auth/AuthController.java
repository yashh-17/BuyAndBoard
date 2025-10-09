package shopnbook.auth;

import shopnbook.utils.PurchaseCollector;
import java.util.Scanner;

public class AuthController {

    public static boolean requireAuth(Scanner sc) {
        System.out.println("1. Signup");
        System.out.println("2. Exit");
        System.out.print("Select option: ");

        int authChoice;
        try {
            authChoice = sc.nextInt();
            sc.nextLine(); 

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter 1 for Signup or 2 to Exit.");
            return false;
        }

        if (authChoice == 1) {
            return handleSignup(sc);
        } else if (authChoice == 2) {
            System.out.println("Thank you for using BuyAndBoard!");
            return false;
        } else {
            System.out.println("Invalid choice. Please select 1 for Signup or 2 to Exit.");
            return false;
        }
    }

    private static boolean handleLogin(Scanner sc) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        try {
            User user = AuthService.login(username, password);
            PurchaseCollector.getInstance().startNewSession(user.getUsername());
            System.out.println("✅ Login successful! Welcome back, " + user.getFullName());
            return true;

        } catch (InvalidCredentialsException e) {
            System.out.println("❌ " + e.getMessage());
            System.out.println("Please check your username and password and try again.");
            return false;
        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred during login. Please try again.");
            return false;
        }
    }

    private static boolean handleSignup(Scanner sc) {
        System.out.println("\n--- SIGNUP ---");

        try {
            String username = getValidatedInput(sc, "Username (min 3 characters)",
                AuthService::validateUsername);

            String password = getValidatedInput(sc,
                "Password (8+ chars, 1 capital, 1 small, 1 number, 1 special char)",
                AuthService::validatePassword);

            String firstName = getValidatedInput(sc, "First Name",
                (input) -> AuthService.validateName(input, "firstName"));

            String lastName = getValidatedInput(sc, "Last Name",
                (input) -> AuthService.validateName(input, "lastName"));

            String email = getValidatedInput(sc, "Email",
                AuthService::validateEmail);

            String phoneNumber = getValidatedInput(sc, "Phone Number (10 digits, optional - press Enter to skip)",
                AuthService::validatePhoneNumber);

            AuthService.registerUser(username, password, firstName, lastName, email, phoneNumber);

            System.out.println("✅ Registration successful! Welcome, " + firstName + " " + lastName + "!");
            System.out.println("Please login with your new credentials.");

            return handleLogin(sc);

        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred during registration. Please try again.");
            return false;
        }
    }

    private static String getValidatedInput(Scanner sc, String prompt, ValidationFunction validator) {
        int retryAttempts = 0;
        final int MAX_RETRY_ATTEMPTS = 3;

        while (true) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();

            try {
                return validator.validate(input);
            } catch (UsernameAlreadyExistsException | InvalidInputException e) {
                System.out.println("❌ " + e.getMessage());
                while (retryAttempts < MAX_RETRY_ATTEMPTS) {
                    System.out.print("Try again? (y/n): ");
                    String retryResponse = sc.nextLine().trim().toLowerCase();

                    if (retryResponse.equals("y") || retryResponse.equals("yes")) {
                        retryAttempts = 0;
                        break;
                    } else if (retryResponse.equals("n") || retryResponse.equals("no")) {
                        System.out.println("Signup cancelled.");
                        throw new RuntimeException("User cancelled signup");
                    } else {
                        retryAttempts++;
                        if (retryAttempts >= MAX_RETRY_ATTEMPTS) {
                            System.out.println("❌ Too many invalid responses. Signup cancelled.");
                            throw new RuntimeException("Too many invalid retry attempts");
                        }
                        System.out.println("❌ Please enter only y or n:");
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred. Please try again.");
                System.out.print("Try again? (y/n): ");
                String choice = sc.nextLine().trim().toLowerCase();

                if (!choice.equals("y") && !choice.equals("yes")) {
                    throw new RuntimeException("User cancelled signup");
                }
            }
        }
    }

    @FunctionalInterface
    private interface ValidationFunction {
        String validate(String input) throws Exception;
    }
}


