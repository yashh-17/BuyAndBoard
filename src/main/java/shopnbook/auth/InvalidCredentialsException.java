package shopnbook.auth;

public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException(String username) {
        super("Invalid username or password for '" + username + "'. Please check your credentials and try again.");
    }
}
