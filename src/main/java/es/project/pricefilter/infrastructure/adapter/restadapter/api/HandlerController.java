package es.project.pricefilter.infrastructure.adapter.restadapter.api;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerController {

    private ProblemDetail buildErrorResponse(HttpStatusCode status, String message) {
        return ProblemDetail.forStatusAndDetail(status, message);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(Exception ex) {
        ProblemDetail errorResponse = buildErrorResponse(HttpStatusCode.valueOf(400), ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(Exception ex) {
        ProblemDetail errorResponse = buildErrorResponse(HttpStatusCode.valueOf(500), ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
