package com.gatway.apiGateway.User.Models.enumPackage;

public enum Status {
    REGISTERED("Registered"),
    LOGGED_OUT("LoggedOut"),
    LOGGED_IN("LoggedIn"),
    BANNED("Banned");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}