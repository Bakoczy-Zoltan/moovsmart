package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;

public class PropertyStateOption {

    private String name;
    private String displayName;

    public PropertyStateOption(PropertyState propertyState) {
        this.name = propertyState.toString();
        this.displayName = propertyState.getDisplayName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
