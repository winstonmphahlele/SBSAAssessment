package za.ac.standardbank.card.exception;

public class ResourceServiceException extends BaseResourceException {

    public ResourceServiceException(int errorCode, String message, String traceId, Throwable cause) {
        super(errorCode, message, traceId, cause);
    }
}
