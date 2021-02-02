package com.holidu.interview.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when {@link com.google.gson.Gson} is unable to parse the response returned from the Census API into
 * the required format
 *
 * @author Nikhil Vibhav
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class JsonParseException extends Exception {
    public JsonParseException(final String message) {
        super(message);
    }

    public JsonParseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
