package com.pracownikService.demo.entity;

public enum TypPracownika {
    FIZYCZNY(1),
    SZEF(2);

    private final int value;
    TypPracownika(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
