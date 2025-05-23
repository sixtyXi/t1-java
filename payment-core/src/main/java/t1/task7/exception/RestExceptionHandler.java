package t1.task7.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import t1.task7.dto.IntegrationErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(IntegrationException.class)
    public IntegrationErrorResponse handleIntegrationExceptions(IntegrationException e) {
        return new IntegrationErrorResponse(e.getMessage(), e.getStatusCode());
    }

}
