package org.hdfc.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger logger =
                LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleInvalidCredentials(
                InvalidCredentialsException ex,
                HttpServletRequest request) {

            logger.warn(
                    "Invalid credentials | method={} | path={} | message={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getMessage()
            );

            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage(),
                    request
            );
        }

        @ExceptionHandler(InvalidTokenException.class)
        public ResponseEntity<ErrorResponse> handleInvalidToken(
                InvalidTokenException ex,
                HttpServletRequest request) {

            logger.warn(
                    "Invalid JWT token | method={} | path={} | message={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getMessage()
            );

            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage(),
                    request
            );
        }

        @ExceptionHandler(MissingTokenException.class)
        public ResponseEntity<ErrorResponse> handleMissingToken(
                MissingTokenException ex,
                HttpServletRequest request) {

            logger.warn(
                    "Missing JWT token | method={} | path={}",
                    request.getMethod(),
                    request.getRequestURI()
            );

            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage(),
                    request
            );
        }

        @ExceptionHandler(SessionInvalidException.class)
        public ResponseEntity<ErrorResponse> handleSessionInvalid(
                SessionInvalidException ex,
                HttpServletRequest request) {

            logger.warn(
                    "Inactive session | method={} | path={} | message={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getMessage()
            );

            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage(),
                    request
            );
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(
                Exception ex,
                HttpServletRequest request) {

            logger.error(
                    "Unexpected application error | method={} | path={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex
            );

            return buildResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred",
                    request
            );
        }

        private ResponseEntity<ErrorResponse> buildResponse(
                HttpStatus status,
                String message,
                HttpServletRequest request) {

            ErrorResponse response = new ErrorResponse(
                    LocalDateTime.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    message,
                    request.getRequestURI()
            );

            return ResponseEntity
                    .status(status)
                    .body(response);
        }
}