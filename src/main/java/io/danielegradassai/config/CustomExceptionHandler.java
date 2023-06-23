package io.danielegradassai.config;

import io.danielegradassai.exception.CustomValidationException;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<Map<String, String>> handle(CustomValidationException customValidationException){
        Map<String, String> errors = customValidationException.getConstraintViolations().stream()
                .collect(Collectors.toMap(constraintViolation -> constraintViolation.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
