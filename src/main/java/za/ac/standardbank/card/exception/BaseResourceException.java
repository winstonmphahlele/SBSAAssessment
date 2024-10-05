package za.ac.standardbank.card.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseResourceException extends Exception {

    private final String traceId;
    private final int errorCode;

    public BaseResourceException(int errorCode, String message, String traceId, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.traceId = traceId;
    }


    @Override
    public String toString() {
        return super.toString() + " [traceId=" + traceId + "]";
    }
}