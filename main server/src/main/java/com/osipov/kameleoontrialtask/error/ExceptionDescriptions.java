package com.osipov.kameleoontrialtask.error;

public enum ExceptionDescriptions {
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_FOUND("User not found"),
    QUOTE_ALREADY_EXISTS("Quote already exists"),
    UNKNOWN_TYPE_OF_SORT("unknown type of sort"),
    QUOTE_NOT_FOUND("Quote not found");

    private final String title;

    ExceptionDescriptions(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}