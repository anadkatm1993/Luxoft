package com.task.controller;

import com.task.exception.EmptyFileException;
import com.task.exception.InvalidFileTypeException;
import com.task.exception.ReadFileException;
import com.task.exception.WriteFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * this class handles all the custom exception
 */
@ControllerAdvice
public class ProcessFileControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = InvalidFileTypeException.class)
    protected ResponseEntity<Object> handleInvalidFileType(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = ReadFileException.class)
    protected ResponseEntity<Object> handleReadFileException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = EmptyFileException.class)
    protected ResponseEntity<Object> handleEmptyFileException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = WriteFileException.class)
    protected ResponseEntity<Object> handleWriteFileException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
