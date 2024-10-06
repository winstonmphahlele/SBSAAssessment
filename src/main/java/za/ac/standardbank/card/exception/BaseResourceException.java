package za.ac.standardbank.card.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseResourceException extends Exception {

    private final int errorCode;

    public BaseResourceException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}