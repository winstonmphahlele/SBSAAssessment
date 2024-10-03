package za.ac.standardbank.card.exception;


public class ResourceNotFoundException extends  RuntimeException{

    private int errorCode;
    private String message;

    public ResourceNotFoundException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
