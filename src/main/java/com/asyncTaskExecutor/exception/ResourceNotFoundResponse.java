package com.asyncTaskExecutor.exception;

/**
 * A generic resource not found response that is usually used along with HTTP
 * status code {@code 404}.
 */
public class ResourceNotFoundResponse {

    /**
     * A generic resource not found message.
     */
    public static final String MESSAGE = "Resource not found.";

    /**
     * Gets the resource not found message.
     *
     * @return the resource not found message.
     */
    public String getMessage() {
        return MESSAGE;
    }
}