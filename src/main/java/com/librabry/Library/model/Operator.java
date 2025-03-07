package com.librabry.Library.model;

public enum Operator {
    EQUALS("="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    IN("IN"),
    LIKE("LIKE");
    private String value;
    Operator(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
