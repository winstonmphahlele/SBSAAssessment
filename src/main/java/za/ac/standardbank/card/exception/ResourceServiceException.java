package za.ac.standardbank.card.exception;

public class ResourceServiceException extends BaseResourceException {

    public ResourceServiceException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
