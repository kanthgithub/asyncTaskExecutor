package com.asyncTaskExecutor.exception;

public class InValidAccountException extends RuntimeException
{
    public static final String code = "ERR_INVALID_ACCOUNT";
    private final String description;

    public InValidAccountException(String description) {
        super(code);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
