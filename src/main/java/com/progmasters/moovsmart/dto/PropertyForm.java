package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PropertyForm {

    private String name;
    private Integer numberOfRooms;
    private Integer price;
    private Integer buildingYear;
    private Double area;
    private String propertyType;
    private String propertyState;
    private String county;
    private String city;
    private Integer zipCode;
    private String street;
    private String streetNumber;
    private String stateForAdmin;
//    private String searchPosition;

    private String description;
    private List<String> imageUrl;
    private List<String> publicId;

    private String owner;

    private Double lngCoord;
    private Double latCoord;

    public PropertyForm() {
    }

    PropertyForm(Property property) {
        this.name = property.getName();
        this.numberOfRooms = property.getNumberOfRooms();
        this.price = property.getPrice();
        this.buildingYear = property.getBuildingYear();
        this.area = property.getArea();
        this.propertyType = property.getPropertyType().getDisplayName();
        this.propertyState = property.getPropertyState().getDisplayName();
        this.county = property.getCounty().getDisplayName();
        this.city = property.getCity();
        this.zipCode = property.getZipCode();
        this.street = property.getStreet();
        this.streetNumber = property.getStreetNumber();
        this.stateForAdmin = property.getStateForAdmin();
//        this.searchPosition = property.getSearchPosition();
        this.description = property.getDescription();
        this.imageUrl = property.getImageUrls();
        this.publicId = property.getPublicIds();
        this.lngCoord = property.getLngCoord();
        this.latCoord = property.getLatCoord();
        this.owner = property.getOwner().getMail();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Integer getBuildingYear() {
        return buildingYear;
    }

    public void setBuildingYear(Integer buildingYear) {
        this.buildingYear = buildingYear;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

//    public String getSearchPosition() {
//        return searchPosition;
//    }
//
//    public void setSearchPosition(String searchPosition) {
//        this.searchPosition = searchPosition;
//    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getLngCoord() {
        return lngCoord;
    }

    public void setLngCoord(Double lngCoord) {
        this.lngCoord = lngCoord;
    }

    public Double getLatCoord() {
        return latCoord;
    }

    public void setLatCoord(Double latCoord) {
        this.latCoord = latCoord;
    }

    public List<String> getPublicId() {
        return publicId;
    }

    public void setPublicId(List<String> publicId) {
        this.publicId = publicId;
    }

    public String getStateForAdmin() {
        return stateForAdmin;
    }

    public void setStateForAdmin(String stateForAdmin) {
        this.stateForAdmin = stateForAdmin;
    }
}
