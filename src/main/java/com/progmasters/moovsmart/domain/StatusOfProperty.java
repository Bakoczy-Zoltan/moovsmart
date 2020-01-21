package com.progmasters.moovsmart.domain;

public enum StatusOfProperty {

    ARCHIVED("archivált"), FORBIDDEN("tiltott"), ACCEPTED("elfogadott"), HOLDING("elfogadásra vár");

    private String displayName;

    StatusOfProperty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
