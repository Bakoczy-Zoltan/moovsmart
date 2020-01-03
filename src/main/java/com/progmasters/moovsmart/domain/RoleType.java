package com.progmasters.moovsmart.domain;

public enum RoleType {
    ROLE_USER("User"),ROLE_ADMIN("admin"),ROLE_SELLER("seller");

    private String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
