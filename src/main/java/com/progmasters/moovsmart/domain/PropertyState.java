package com.progmasters.moovsmart.domain;

public enum PropertyState {
    NEW("Új"), RENEWED("Felújított"), RENEWABLE("Felújítandó"),
    UNFINISHED("Befejezetlen");

    private String displayName;

    PropertyState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
