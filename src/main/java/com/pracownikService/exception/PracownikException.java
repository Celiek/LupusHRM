package com.pracownikService.exception;

public class PracownikException extends RuntimeException{

    private final PracownikError error;

    public PracownikException(PracownikError error,String message){
        super(message);
        this.error = error;
    }

    public PracownikError getError(){
        return error;
    }
}
