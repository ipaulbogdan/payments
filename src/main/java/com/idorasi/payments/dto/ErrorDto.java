package com.idorasi.payments.dto;

public class ErrorDto {

    private Exception exception;

    public ErrorDto(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


}
