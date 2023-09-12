package com.marvic.factsigner;

import com.marvic.factsigner.exception.APIException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ResponseBody
//    @ExceptionHandler(EmployeeNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
//        return ex.getMessage();
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(BadCredentialsException exception,
                                                               WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception exception,
                                                                         WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        String path = ((ServletWebRequest)webRequest).getRequest().getRequestURI();
        errorResponse.setPath(path);
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceExistsException(ResourceExistsException exception,
                                                                       WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        String path = ((ServletWebRequest)webRequest).getRequest().getRequestURI();
        errorResponse.setPath(path);
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, WebRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(apiErrorResponse);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleAPIException(APIException exception,
                                                            WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
