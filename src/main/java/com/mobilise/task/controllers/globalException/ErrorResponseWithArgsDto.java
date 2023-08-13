package com.mobilise.task.controllers.globalException;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class ErrorResponseWithArgsDto {
    private String status;
    private HashSet<ErrorWithArguments> errors;

    public ErrorResponseWithArgsDto() {
        this.errors = new HashSet<>();
        this.setStatus("ERROR");
    }

    public static ResponseEntity<Object> build(HttpStatus status, List<FieldError> errors) {
        ErrorResponseWithArgsDto errorResponseDto = new ErrorResponseWithArgsDto();
        for (FieldError fieldError : errors) {
            String message = fieldError.getDefaultMessage();
            Object[] args = {fieldError.getField(), fieldError.getRejectedValue()};
            errorResponseDto.addError(message, args);
        }
        return ResponseEntity.status(status).body(errorResponseDto);
    }

    public static ResponseEntity<Object> build(HttpStatus status, ErrorWithArguments... errorWithArguments) {
        ErrorResponseWithArgsDto errorResponseDto = new ErrorResponseWithArgsDto();
        errorResponseDto.setErrors(Sets.newHashSet(errorWithArguments));
        return ResponseEntity.status(status).body(errorResponseDto);
    }

    public void addError(String message, Object[] args) {
        if (this.errors == null)
            this.errors = new HashSet<>();
        this.errors.add(new ErrorWithArguments(message, args));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class ErrorWithArguments {
        private String error;
        private Object[] args;

        public ErrorWithArguments(String error) {
            this.error = error;
            this.args = new Object[]{};
        }
    }
}
