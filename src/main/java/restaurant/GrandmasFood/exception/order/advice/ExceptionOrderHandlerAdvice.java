package restaurant.GrandmasFood.exception.order.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.exception.ErrorInfo;
import restaurant.GrandmasFood.exception.IErrorCode;
import restaurant.GrandmasFood.exception.order.OrderNotFoundException;
import restaurant.GrandmasFood.exception.order.OrderValidationException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionOrderHandlerAdvice {

    @Autowired
    DateTimeConverter dateTimeConverter;

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorInfo> handlerOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ErrorInfo.builder()
                        .code(IErrorCode.NOT_FOUND_CODE_ERROR)
                        .timestamp(dateTimeConverter.formatDateTimeToIso8601(LocalDateTime.now()))
                        .description(e.getMessage())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<ErrorInfo> handlerOrderValidationException(OrderValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ErrorInfo.builder()
                        .code(IErrorCode.BAD_REQUEST_CODE_ERROR)
                        .timestamp(dateTimeConverter.formatDateTimeToIso8601(LocalDateTime.now()))
                        .description(e.getMessage())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }
}
