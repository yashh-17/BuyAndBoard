package shopnbook.auth;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AuthService {
    private static Map<String, User> userDatabase = new HashMap<>();
    private static final String USER_DB_FILE = "users.db"; // simple username:password per line

    static {
        loadUsersFromFile();
    }

    public static boolean signup(String username, String password) {
        if (username == null || password == null) return false;
        if (userDatabase.containsKey(username)) {
            return false;
        }
        userDatabase.put(username, new User(username, password));
        saveUserToFile(username, password);
        return true;
    }

    public static boolean login(String username, String password) {
        if (username == null || password == null) return false;
        User user = userDatabase.get(username);
        if (user == null) return false;
        return password.equals(user.getPassword());
    }

    private static void loadUsersFromFile() {
        Path path = Paths.get(USER_DB_FILE);
        if (!Files.exists(path)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
                String[] parts = trimmed.split(":", 2);
                if (parts.length == 2) {
                    String u = parts[0];
                    String p = parts[1];
                    if (!u.isEmpty()) {
                        userDatabase.put(u, new User(u, p));
                    }
                }
            }
        } catch (IOException e) {
            // best-effort: ignore load errors
        }
    }

    private static void saveUserToFile(String username, String password) {
        Path path = Paths.get(USER_DB_FILE);
        String line = username + ":" + password + System.lineSeparator();
        try {
            Files.write(path, line.getBytes(StandardCharsets.UTF_8),
                Files.exists(path) ? new StandardOpenOption[]{StandardOpenOption.APPEND} : new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND});
        } catch (IOException e) {
            // best-effort: ignore save errors
        }
    }
}


