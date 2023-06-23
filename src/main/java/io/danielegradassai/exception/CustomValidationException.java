package io.danielegradassai.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

@Getter
public class CustomValidationException extends RuntimeException{

    private final Set<ConstraintViolation<?>> constraintViolations;

    public CustomValidationException(Object constraintViolations) {
        this.constraintViolations = (Set<ConstraintViolation<?>>) constraintViolations;
    }

}
