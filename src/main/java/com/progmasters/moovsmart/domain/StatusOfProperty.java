package com.progmasters.moovsmart.domain;

public enum StatusOfProperty {

    ARCHIVED("archivált"),
    FORBIDDEN("tiltott"),
    HOLDING("elfogadásra vár"),
    ACCEPTED("elfogadott");

    private String displayName;

    StatusOfProperty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
