package t1.task7.exception;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {
    private final int statusCode;

    public IntegrationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
