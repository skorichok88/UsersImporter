package com.importer.web;

import com.importer.exception.IncorrectFileTypeException;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    final static Logger logger = Logger.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(IncorrectFileTypeException.class)
    public final ResponseEntity<Object> handleIncorrectFileTypeException(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Wrong file type. Accepted only CSV file type");
        logger.error(ex);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public final ResponseEntity<Object> handleSizeLimitExceededException(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Uploaded file size exceeds the configured maximum");
        logger.error(ex);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}