package com.pracownikService.exception;

public class CzasPracyException extends RuntimeException {

    private final CzasPracyError error;

    public CzasPracyException(CzasPracyError error, String message) {
        super(message);
        this.error = error;
    }
    public CzasPracyError getError(){return  error;}
}
