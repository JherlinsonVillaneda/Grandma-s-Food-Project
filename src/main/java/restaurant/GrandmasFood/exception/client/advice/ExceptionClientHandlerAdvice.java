package restaurant.GrandmasFood.exception.client.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.exception.ErrorInfo;
import restaurant.GrandmasFood.exception.IErrorCode;
import restaurant.GrandmasFood.exception.client.ClientBadRequestException;
import restaurant.GrandmasFood.exception.client.ConflictClientException;
import restaurant.GrandmasFood.exception.client.InternalServerErrorException;
import restaurant.GrandmasFood.exception.client.NotFoundException;

import java.time.LocalDateTime;
@RestControllerAdvice
public class ExceptionClientHandlerAdvice {

    @Autowired
    DateTimeConverter dateConverter;


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> notFoundInfoResponse(NotFoundException ex) {
        ErrorInfo notFoundInfo = new ErrorInfo(IErrorCode.NOT_FOUND_CODE_ERROR, dateConverter.formatDateTimeToIso8601(LocalDateTime.now()), ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(notFoundInfo, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ClientBadRequestException.class)
    public ResponseEntity<ErrorInfo> handlerClientValidationException(ClientBadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ErrorInfo.builder()
                        .code(IErrorCode.BAD_REQUEST_CODE_ERROR)
                        .timestamp(dateConverter.formatDateTimeToIso8601(LocalDateTime.now()))
                        .description(e.getMessage())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(ConflictClientException.class)
    public ResponseEntity<ErrorInfo> conflictClientResponse(ConflictClientException e){
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(ErrorInfo.builder()
                .code(IErrorCode.CONFLICT_CODE_ERROR)
                .timestamp(dateConverter.formatDateTimeToIso8601(LocalDateTime.now()))
                .description(e.getMessage())
                .exception(e.getClass().getSimpleName())
                .build());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorInfo> InternalServerErrorResponse(InternalServerErrorException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ErrorInfo.builder()
                .code(IErrorCode.INTERNAL_SERVER_CODE_ERROR)
                .timestamp(dateConverter.formatDateTimeToIso8601(LocalDateTime.now()))
                .description(e.getMessage())
                .exception(e.getClass().getSimpleName())
                .build());
    }




}
