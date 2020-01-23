package com.progmasters.moovsmart.domain;

public enum PropertyType {
    HOUSE("Ház"), APARTMENT("Lakás"), PANEL("Panellakás"),
    OFFICE("Iroda");

    private String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
