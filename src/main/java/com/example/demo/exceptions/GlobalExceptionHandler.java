package com.example.demo.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {


        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
        problemDetail.setProperty("Error detail: ", e.getMessage());
        return problemDetail;

    }
}
