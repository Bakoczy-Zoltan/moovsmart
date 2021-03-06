package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.County;
import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;

import java.util.List;

public class PropertyDetails {

    private Long id;
    private String name;
    private Integer numberOfRooms;
    private Integer price;
    private Integer buildingYear;
    private Double area;
    private String propertyType;
    private String propertyState;
    private String county;
    private Integer zipCode;
    private String street;
    private String streetNumber;
    private String city;
    private Double lngCoord;
    private Double latCoord;

    private String description;
    private List<String> imageUrl;
    private List<String> publicId;
    private Long userId;
    private String status;

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
        this.street = property.getStreet();
        this.streetNumber = property.getStreetNumber();
        this.latCoord = property.getLatCoord();
        this.lngCoord = property.getLngCoord();
        this.description = property.getDescription();
        this.imageUrl = property.getImageUrls();
        this.publicId = property.getPublicIds();
        this.city = property.getCity();
        if(property.getOwner() != null){
            this.userId = property.getOwner().getId();
        }
        if(property.getStatus()!= null){
            this.status = property.getStatus().getDisplayName();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getPublicId() {
        return publicId;
    }

    public void setPublicId(List<String> publicId) {
        this.publicId = publicId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
