package za.ac.standardbank.card.exception;

public class ResourceAlreadyExistsException extends BaseResourceException {
    public ResourceAlreadyExistsException(int errorCode, String message, String traceId, Throwable cause) {
        super(errorCode, message, traceId, cause);
    }
}
