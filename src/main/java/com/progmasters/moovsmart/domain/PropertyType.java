package com.progmasters.moovsmart.domain;

public enum PropertyType {
    HOUSE("Ház"), APARTMENT("Lakás"), PANEL("Panellakás"),
    LAND("Telek");

    private String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
