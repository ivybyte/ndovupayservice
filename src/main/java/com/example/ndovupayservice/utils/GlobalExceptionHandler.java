package com.example.ndovupayservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static String BACKEND_ERROR =  "Failed !!  Backend Error";
    private static String ACCESS_DENIED_ERROR =  "Access Denied - User Not AUthorised To Perform Action- ";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardJsonResponse> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);
        response.setMessage("message",BACKEND_ERROR, response);
        response.setStatus(500);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardJsonResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ex.printStackTrace();
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);
        response.setMessage("message", "Invalid credentials,", response);
        response.setStatus(403);  // Setting status as 403 Forbidden
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }




    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardJsonResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);
        response.setMessage("message",ex.getMessage(), response);
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


//    @ExceptionHandler( AuthenticationException.class)
//    public ResponseEntity<StandardJsonResponse> handleAuthenticationException(AuthenticationException ex) {
//        ex.printStackTrace();
//        StandardJsonResponse response = new StandardJsonResponse();
//        response.setSuccess(false);
//        response.setMessage("message", "Invalid Token", response);
//        response.setStatus(401);
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardJsonResponse> handleException(Exception ex) {
        ex.printStackTrace();
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);
        response.setMessage("message",BACKEND_ERROR, response);
        response.setStatus(400);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardJsonResponse>  handleValidationExceptions(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        response.setMessage("message",errors,response);
        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardJsonResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);
        response.setMessage("message", BACKEND_ERROR,response);
        response.setStatus(400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<StandardJsonResponse> handleAccessDeniedException(AccessDeniedException ex) {
//        ex.printStackTrace();
//        StandardJsonResponse response = new StandardJsonResponse();
//        response.setSuccess(false);
//
//        response.setMessage("message", ACCESS_DENIED_ERROR,response);
//        response.setStatus(403);
//        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//    }
//
//
@ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<StandardJsonResponse> handleAccessDeniedException(IllegalAccessException ex) {
        ex.printStackTrace();
        StandardJsonResponse response = new StandardJsonResponse();
        response.setSuccess(false);

        response.setMessage("message", ACCESS_DENIED_ERROR,response);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
