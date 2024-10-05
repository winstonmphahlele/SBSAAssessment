package za.ac.standardbank.card.exception;

public class RepositoryException extends RuntimeException {

    private final String traceId;

    public RepositoryException( String message, String traceId) {
        super(message);
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return super.toString() + " [traceId=" + traceId + "]";
    }
}
