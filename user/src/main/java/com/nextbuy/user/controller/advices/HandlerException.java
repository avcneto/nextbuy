package com.nextbuy.user.controller.advices;

import com.nextbuy.user.exception.BadRequestException;
import com.nextbuy.user.exception.FailedDependencyException;
import com.nextbuy.user.exception.ForbiddenException;
import com.nextbuy.user.exception.NotFoundException;
import com.nextbuy.user.exception.ResourceAlreadyExistsException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FAILED_DEPENDENCY;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public record HandlerException(
        MessageSource messageSource
) {
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = messageSource.getMessage(error, LocaleContextHolder.getLocale());
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @ResponseStatus(CONFLICT)
  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ProblemDetail> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
    return ResponseEntity.status(CONFLICT).body(ex.getBody());
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND).body(ex.getBody());
  }

  @ResponseStatus(FAILED_DEPENDENCY)
  @ExceptionHandler(FailedDependencyException.class)
  public ResponseEntity<ProblemDetail> handleFailedDependencyException(FailedDependencyException ex) {
    return ResponseEntity.status(FAILED_DEPENDENCY).body(ex.getBody());
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException ex) {
    return ResponseEntity.status(BAD_REQUEST).body(ex.getBody());
  }

  @ResponseStatus(FORBIDDEN)
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ProblemDetail> handleForbiddenException(ForbiddenException ex) {
    return ResponseEntity.status(FORBIDDEN).body(ex.getBody());
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
    problemDetail.setDetail("Invalid JSON input");
    problemDetail.setTitle("Invalid JSON input");
    problemDetail.setDetail(ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST).body(problemDetail);
  }

}
