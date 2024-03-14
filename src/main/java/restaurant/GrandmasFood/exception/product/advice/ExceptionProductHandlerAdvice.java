package restaurant.GrandmasFood.exception.product.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.exception.ErrorInfo;
import restaurant.GrandmasFood.exception.IErrorCode;
import restaurant.GrandmasFood.exception.product.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionProductHandlerAdvice {

    @Autowired
    DateTimeConverter dateConverter;

    @ExceptionHandler(NotProductFoundException.class)
    public ResponseEntity<ErrorInfo> notProductFoundInfoResponse (NotProductFoundException ex){
        ErrorInfo errorInfo = new ErrorInfo(IErrorCode.NOT_FOUND_CODE_ERROR, dateConverter.formatDateTimeToIso8601(LocalDateTime.now()), ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> productAlreadyExistsInfoResponse (ProductAlreadyExistsException ex){
        ErrorInfo errorInfo = new ErrorInfo(IErrorCode.NAME_ALREADY_EXISTS_CODE_ERROR, dateConverter.formatDateTimeToIso8601(LocalDateTime.now()), ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestProductException.class)
    public ResponseEntity<ErrorInfo> productBadRequestInfoResponse (BadRequestProductException ex){
        ErrorInfo errorInfo = new ErrorInfo(IErrorCode.BAD_REQUEST_CODE_ERROR, dateConverter.formatDateTimeToIso8601(LocalDateTime.now()), ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictProductException.class)
    public ResponseEntity<ErrorInfo> productConflictInfoResponse (ConflictProductException ex){
        ErrorInfo errorInfo = new ErrorInfo(IErrorCode.CONFLICT_CODE_ERROR, dateConverter.formatDateTimeToIso8601(LocalDateTime.now()), ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductValidationException.class)
    public ResponseEntity<ErrorInfo> productValidationInfoResponse(ProductValidationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ErrorInfo.builder()
                        .code(IErrorCode.BAD_REQUEST_CODE_ERROR)
                        .timestamp(dateConverter.formatDateTimeToIso8601(LocalDateTime.now()))
                        .description(ex.getMessage())
                        .exception(ex.getClass().getSimpleName())
                        .build());
    }
}
