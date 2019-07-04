package com.idorasi.payments.controller;

import com.idorasi.payments.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.net.UnknownHostException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleMissingEntity(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e));
    }

    @ExceptionHandler(value = UnknownHostException.class)
    protected  ResponseEntity<ErrorDto> handleNoInternetConnection(RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorDto(e));
    }

    @ExceptionHandler(value = ResourceAccessException.class)
    protected  ResponseEntity<ErrorDto> handleNoAccesException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorDto(e));
    }
}
