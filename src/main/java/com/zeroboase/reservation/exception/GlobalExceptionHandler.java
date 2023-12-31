package com.zeroboase.reservation.exception;

import static com.zeroboase.reservation.exception.ErrorCode.ACCESS_DENIED;
import static com.zeroboase.reservation.exception.ErrorCode.INTERNAL_ERROR;
import static com.zeroboase.reservation.exception.ErrorCode.VALIDATION_FAIL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorResponse> handleAccountException(ReservationException e) {
        log.error("{} is occurred.", e.getErrorCode(), e);
        return getErrorResponseResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred.", e);
        return getErrorResponseResponseEntity(VALIDATION_FAIL,
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException is occurred.", e);
        return getErrorResponseResponseEntity(VALIDATION_FAIL, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
        AccessDeniedException e) {
        log.error("AccessDeniedException is occurred.", e);
        return getErrorResponseResponseEntity(ACCESS_DENIED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception is occurred.", e);

        return getErrorResponseResponseEntity(INTERNAL_ERROR);
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(
        ErrorCode errorCode) {
        return new ResponseEntity<>(ErrorResponse.from(errorCode),
            HttpStatus.valueOf(errorCode.getStatus()));
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(
        ErrorCode errorCode, String message) {
        return new ResponseEntity<>(ErrorResponse.builder()
            .status(errorCode.getStatus())
            .errorCode(errorCode)
            .description(message)
            .build(),
            HttpStatus.valueOf(errorCode.getStatus()));
    }
}
