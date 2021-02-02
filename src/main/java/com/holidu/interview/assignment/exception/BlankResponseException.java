package com.holidu.interview.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the Census API returns a blank response
 *
 * @author Nikhil Vibhav
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class BlankResponseException extends Exception {
    public BlankResponseException(final String message) {
        super(message);
    }

    public BlankResponseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
