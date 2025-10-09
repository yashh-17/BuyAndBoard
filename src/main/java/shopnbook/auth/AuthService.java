package shopnbook.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123"));
        users.add(new User("user", "password"));
        users.add(new User("test", "test123"));
    }

    public static boolean signup(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty()) {
            return false;
        }

        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                return false;
            }
        }

        users.add(new User(username, password));
        return true;
    }

    public static boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Found matching user
            }
        }

        return false; 
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}


