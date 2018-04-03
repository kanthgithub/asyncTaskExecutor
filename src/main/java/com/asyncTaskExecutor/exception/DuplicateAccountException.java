package com.asyncTaskExecutor.exception;

public class DuplicateAccountException extends RuntimeException
{
    public static final String code = "ERR_DUPLICATE_ACCOUNT";
    private final String description;

    public DuplicateAccountException(String description) {
        super(code);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
