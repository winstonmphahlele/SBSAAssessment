package za.ac.standardbank.card.exception;

public class ResourceAlreadyExistsException extends BaseResourceException {
    public ResourceAlreadyExistsException(int errorCode, String message, Throwable cause) {
        super(errorCode, message,  cause);
    }
}
