package com.progmasters.moovsmart.dto;

import java.util.List;

public class PropertyInitFormData {
    private List<CountyOption> counties;
    private List<PropertyTypeOption> propertyTypes;
    private List<PropertyStateOption> propertyStates;

    public PropertyInitFormData(List<CountyOption> counties,
                                List<PropertyTypeOption> propertyTypes,
                                List<PropertyStateOption> propertyStates) {
        this.counties = counties;
        this.propertyTypes = propertyTypes;
        this.propertyStates = propertyStates;
    }

    public List<CountyOption> getCounties() {
        return counties;
    }

    public List<PropertyTypeOption> getPropertyTypes() {
        return propertyTypes;
    }

    public List<PropertyStateOption> getPropertyStates() {
        return propertyStates;
    }
}
