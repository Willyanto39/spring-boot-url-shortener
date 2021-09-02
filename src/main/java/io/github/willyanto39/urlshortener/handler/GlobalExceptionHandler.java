package io.github.willyanto39.urlshortener.handler;

import io.github.willyanto39.urlshortener.exception.InvalidUrlException;
import io.github.willyanto39.urlshortener.exception.UrlNotFoundException;
import io.github.willyanto39.urlshortener.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String message = ex.getFieldError().getDefaultMessage();
    ApiResponse<Void> response = ApiResponse.error(message);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(InvalidUrlException.class)
  public ResponseEntity<ApiResponse<Void>> handleInvalidUrlException(InvalidUrlException e) {
    String message = e.getMessage();
    ApiResponse<Void> response = ApiResponse.error(message);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(UrlNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleUrlNotFoundException(UrlNotFoundException e) {
    ApiResponse<Void> response = ApiResponse.error("Url not found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
