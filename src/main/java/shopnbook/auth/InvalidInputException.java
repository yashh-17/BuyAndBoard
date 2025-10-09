package shopnbook.auth;

public class InvalidInputException extends AuthenticationException {
    public InvalidInputException(String fieldName) {
        super("The field '" + fieldName + "' cannot be empty. Please provide a valid value.");
    }

    public InvalidInputException(String fieldName, String reason) {
        super("The field '" + fieldName + "' is invalid: " + reason);
    }
}
