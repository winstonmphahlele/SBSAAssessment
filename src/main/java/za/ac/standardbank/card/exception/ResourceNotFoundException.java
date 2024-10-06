package za.ac.standardbank.card.exception;


public class ResourceNotFoundException extends  BaseResourceException{

    public ResourceNotFoundException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
