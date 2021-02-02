package com.holidu.interview.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there's an issue with the REST call to the census API endpoint, e.g. timeout, service
 * unavailable
 *
 * @author Nikhil Vibhav
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RestOperationException extends Exception {
    public RestOperationException(final String message) {
        super(message);
    }

    public RestOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
