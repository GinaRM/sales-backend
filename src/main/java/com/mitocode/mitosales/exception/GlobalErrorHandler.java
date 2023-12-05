package com.mitocode.mitosales.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest req) {

        CustomErrorResponse customErrorResponse =
                new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(),req.getDescription(false));
        return  new ResponseEntity<>(customErrorResponse, HttpStatus.PRECONDITION_FAILED);

    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //Since Spring boot 3
//    @ExceptionHandler(ModelNotFoundException.class)
//    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex, WebRequest req) {
//        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
//        pdl.setTitle("Model Not Found Exception");
//        pd.setType(URI.create(req.getContextPath()));
//        return  pd;
//    }

    @ExceptionHandler({ArithmeticException.class, IndexOutOfBoundsException.class})
    public ResponseEntity<CustomErrorResponse> handleArithmeticException(ArithmeticException ex, WebRequest req){
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String message =
                ex.getBindingResult().getFieldErrors().stream()
                        .map( error -> error.getField() + " : " + error.getDefaultMessage())
                        .collect(Collectors.joining(", "));

        CustomErrorResponse customErrorResponse =
                new CustomErrorResponse(LocalDateTime.now(), message,request.getDescription(false));
        return  new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
