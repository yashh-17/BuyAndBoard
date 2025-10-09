package shopnbook.auth;

public class UsernameAlreadyExistsException extends AuthenticationException {
    public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' is already taken. Please choose a different username.");
    }
}
