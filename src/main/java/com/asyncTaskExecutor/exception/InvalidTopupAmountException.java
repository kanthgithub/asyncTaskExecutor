package com.asyncTaskExecutor.exception;

public class InvalidTopupAmountException extends RuntimeException
{
    public static final String code = "ERR_INVALID_TOPUP_AMOUNT";
    private final String description;

    public InvalidTopupAmountException(String description) {
        super(code);
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
