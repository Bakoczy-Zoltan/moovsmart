package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.County;

public class CountyOption {

    private String name;
    private String displayName;

    public CountyOption(County county) {
        this.name = county.toString();
        this.displayName = county.getDisplayName();
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
