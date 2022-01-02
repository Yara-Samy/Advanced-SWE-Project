package com.uber.software.models;

public enum Holidays {
    STRING_ONE("06-10"), STRING_TWO("02-05"), STRING_THREE("08-10"), STRING_FOUR("30-06");

    private String date;

    Holidays(String date) {
        this.date =date;
    }

    public String getDate() {
        return date;
    }
}
