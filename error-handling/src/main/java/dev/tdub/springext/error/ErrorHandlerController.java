package dev.tdub.springext.error;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import dev.tdub.springext.error.dto.ErrorResponseDto;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import dev.tdub.springext.error.exceptions.AuthorizationException;
import dev.tdub.springext.error.exceptions.ClientException;
import dev.tdub.springext.error.exceptions.InternalServerException;
import dev.tdub.springext.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandlerController extends ResponseEntityExceptionHandler {
  private final RequestIdSupplier ridSupplier;

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
    log.debug("Client error.", ex);
    return ResponseEntity.status(UNAUTHORIZED)
        .body(new ErrorResponseDto("Invalid Credentials.", ridSupplier.get()));
  }

  @ExceptionHandler(value = {AuthorizationException.class})
  public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException ex) {
    log.debug("Client error.", ex);
    return ResponseEntity.status(FORBIDDEN)
        .body(new ErrorResponseDto(ex.getMessage(), ridSupplier.get()));
  }

  @ExceptionHandler(value = {ClientException.class})
  public ResponseEntity<ErrorResponse> handleClientException(ClientException ex) {
    log.debug("Client error.", ex);
    return ResponseEntity.status(BAD_REQUEST)
        .body(new ErrorResponseDto(ex.getMessage(), ridSupplier.get()));
  }

  @ExceptionHandler(value = {InternalServerException.class})
  public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException ex) {
    log.error("Server error.", ex);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(new ErrorResponseDto("Internal Error. Please contact the ora team", ridSupplier.get()));
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    log.debug("Client error.", ex);
    return ResponseEntity.status(NOT_FOUND)
        .body(new ErrorResponseDto(ex.getMessage(), ridSupplier.get()));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.debug("Client error.", ex.getCause());

    String message = "Invalid request, check the documentation for more details";
    if (ex.getCause() instanceof MismatchedInputException) {
      List<String> targetFields =
          Arrays.stream(((MismatchedInputException)ex.getCause())
              .getTargetType()
              .getDeclaredFields())
              .map(Field::getName)
              .toList();
      message = "Provided request parameter not contained within configured attribute list " + targetFields;
    }

    return ResponseEntity.badRequest().body(new ErrorResponseDto(message, ridSupplier.get()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.debug("Client error.", ex);
    String error = ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining("."));
    return ResponseEntity.badRequest().body(new ErrorResponseDto(error, ridSupplier.get()));
  }
}
