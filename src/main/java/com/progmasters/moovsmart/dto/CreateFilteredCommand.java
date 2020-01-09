package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;

public class CreateFilteredCommand {
    private int minArea;
    private int minPrice;
    private int maxArea;
    private int maxPrice;
    private PropertyState propertyState;
    private PropertyType propertyType;
    private String city;
    private int numberOfRooms;

    public CreateFilteredCommand() {
    }

    public CreateFilteredCommand(int minArea, int minPrice,
                                 int maxArea, int maxPrice, PropertyState propertyState,
                                 PropertyType propertyType, String city,
                                 int numberOfRooms) {
        this.minArea = minArea;
        this.minPrice = minPrice;
        this.maxArea = maxArea;
        this.maxPrice = maxPrice;
        this.propertyState = propertyState;
        this.propertyType = propertyType;
        this.city = city;
        this.numberOfRooms = numberOfRooms;
    }

    public int getMinArea() {
        return minArea;
    }

    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
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

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
