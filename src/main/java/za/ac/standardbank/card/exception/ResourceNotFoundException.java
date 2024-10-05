package za.ac.standardbank.card.exception;


public class ResourceNotFoundException extends  BaseResourceException{

    public ResourceNotFoundException(int errorCode, String message, String traceId, Throwable cause) {
        super(errorCode, message, traceId, cause);
    }
}
