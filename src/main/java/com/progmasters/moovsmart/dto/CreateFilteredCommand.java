package com.progmasters.moovsmart.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;

@JsonIgnoreProperties
public class CreateFilteredCommand {

    private Double minArea;
    private Integer minPrice;
    private Double maxArea;
    private Integer maxPrice;
    private PropertyState propertyState;
    private PropertyType propertyType;
    private String city;
    private Integer numberOfRooms;

    public Double getMinArea() {
        return minArea;
    }

    public void setMinArea(Double minArea) {
        this.minArea = minArea;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(Double maxArea) {
        this.maxArea = maxArea;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public PropertyState getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(PropertyState propertyState) {
        this.propertyState = propertyState;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
