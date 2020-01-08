package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.County;
import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;

import java.util.List;

public class PropertyDetails {

    private long id;
    private String name;
    private int numberOfRooms;
    private int price;
    private int buildingYear;
    private double area;
    private String propertyType;
    private String propertyState;
    private String county;
    private int zipCode;
//    private String street;
    private String searchPosition;
    private String description;
    private List<String> imageUrl;

    public PropertyDetails() {
    }

    public PropertyDetails(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.numberOfRooms = property.getNumberOfRooms();
        this.price = property.getPrice();
        this.buildingYear = property.getBuildingYear();
        this.area = property.getArea();
        if (property.getPropertyType() != null) {
            this.propertyType = property.getPropertyType().getDisplayName();
        }
        if (property.getPropertyState() != null) {
            this.propertyState = property.getPropertyState().getDisplayName();
        }
        if (property.getCounty() != null) {
            this.county = property.getCounty().getDisplayName();
        }
        this.zipCode = property.getZipCode();
//        this.street = property.getStreet();
        this.searchPosition = property.getSearchPosition();
        this.description = property.getDescription();
        this.imageUrl = property.getImageUrls();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBuildingYear() {
        return buildingYear;
    }

    public void setBuildingYear(int buildingYear) {
        this.buildingYear = buildingYear;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(String propertyState) {
        this.propertyState = propertyState;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }

    public String getSearchPosition() {
        return searchPosition;
    }

    public void setSearchPosition(String searchPosition) {
        this.searchPosition = searchPosition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

}
