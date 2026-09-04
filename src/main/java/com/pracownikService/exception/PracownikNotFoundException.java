package com.pracownikService.exception;

public class PracownikNotFoundException extends RuntimeException{
    public PracownikNotFoundException(String message){
        super(message);
    }
}
