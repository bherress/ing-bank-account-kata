package fr.ing.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedOperationException.class)
    protected ResponseEntity<CustomErrorResponse> handleEntityNotFound(
            UnauthorizedOperationException ex) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        HttpStatus status;
        if(ErrorTypeEnum.NOT_VALID_AMOUNT.equals(ex.getErrorTypeEnum())
                || ErrorTypeEnum.NEGATIVE_BALANCE.equals(ex.getErrorTypeEnum())){
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        errors.setStatus(status.value());

        return new ResponseEntity<>(errors, status);
    }
}
