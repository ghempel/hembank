package com.hempel.hembank.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("Validation Error");
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        fieldErrors.stream().forEach(f -> errorMessage.getFieldsErros().add(new FieldErrorMessage(f.getField(), f.getDefaultMessage())));

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(IllegalArgumentException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(errorMessage);
    }
}

@Getter
@Setter
@NoArgsConstructor
class ErrorMessage {

    private int statusCode;
    private String message;
    private final List<FieldErrorMessage> fieldsErros = new ArrayList<>();
}

@Getter
@NoArgsConstructor
@AllArgsConstructor
class FieldErrorMessage {
    private String field;
    private String message;
}
