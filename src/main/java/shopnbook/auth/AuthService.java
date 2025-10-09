package shopnbook.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AuthService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123", "Admin", "User", "admin@shopnbook.com", "1234567890"));
        users.add(new User("user", "password", "Regular", "User", "user@shopnbook.com", "0987654321"));
        users.add(new User("test", "test123", "Test", "User", "test@shopnbook.com", "1122334455"));
    }

    public static void registerUser(String username, String password, String firstName,
                                   String lastName, String email, String phoneNumber)
            throws UsernameAlreadyExistsException, InvalidInputException {

        validateRegistrationInput(username, password, firstName, lastName, email, phoneNumber);

        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                throw new UsernameAlreadyExistsException(username);
            }
        }

        User newUser = new User(username, password, firstName, lastName, email, phoneNumber);
        users.add(newUser);
    }

    public static User login(String username, String password)
            throws InvalidCredentialsException, InvalidInputException {

        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("username", "cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidInputException("password", "cannot be empty");
        }

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        throw new InvalidCredentialsException(username);
    }

    private static void validateRegistrationInput(String username, String password,
                                                 String firstName, String lastName,
                                                 String email, String phoneNumber)
            throws InvalidInputException {

        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("username");
        }
        if (password == null || password.length() < 6) {
            throw new InvalidInputException("password", "must be at least 6 characters long");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidInputException("firstName");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidInputException("lastName");
        }
        if (email == null || !email.contains("@")) {
            throw new InvalidInputException("email", "must contain @ symbol");
        }
        if (phoneNumber != null && !phoneNumber.matches("\\d{10}")) {
            throw new InvalidInputException("phoneNumber", "must be 10 digits");
        }
    }

    public static String validateUsername(String username) throws InvalidInputException, UsernameAlreadyExistsException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("username", "cannot be empty");
        }
        if (username.length() < 3) {
            throw new InvalidInputException("username", "must be at least 3 characters long");
        }
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                throw new UsernameAlreadyExistsException(username);
            }
        }
        return username.trim();
    }

    public static String validatePassword(String password) throws InvalidInputException {
        if (password == null || password.length() < 8) {
            throw new InvalidInputException("password", "must be at least 8 characters long");
        }

        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]").matcher(password).find();

        if (!hasUpper) {
            throw new InvalidInputException("password", "must contain at least 1 capital letter (A-Z)");
        }
        if (!hasLower) {
            throw new InvalidInputException("password", "must contain at least 1 small letter (a-z)");
        }
        if (!hasDigit) {
            throw new InvalidInputException("password", "must contain at least 1 number (0-9)");
        }
        if (!hasSpecial) {
            throw new InvalidInputException("password", "must contain at least 1 special character (!@#$%^&*)");
        }

        return password;
    }

    public static String validateName(String name, String fieldName) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, "cannot be empty");
        }
        return name.trim();
    }

    public static String validateEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("email", "cannot be empty");
        }
        if (!email.contains("@")) {
            throw new InvalidInputException("email", "must contain @ symbol");
        }
        if (!email.contains(".")) {
            throw new InvalidInputException("email", "must contain a domain (e.g., .com)");
        }
        return email.trim().toLowerCase();
    }

    public static String validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }
        if (!phoneNumber.matches("\\d{10}")) {
            throw new InvalidInputException("phoneNumber", "must be exactly 10 digits");
        }
        return phoneNumber.trim();
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}


