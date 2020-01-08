package com.progmasters.moovsmart.domain;

import com.progmasters.moovsmart.dto.PropertyForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer numberOfRooms;
    private Integer price;
    private int buildingYear;
    private double area;
    private PropertyType propertyType;
    private PropertyState propertyState;
    private County county;
    private int zipCode;
    private String street;
    private String streetNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "boolean default true")
    private boolean isValid;

    @ManyToOne
    private UserProperty owner;

    private double lngCoord;
    private double latCoord;

    @Column(name = "imagesUrl")
    @ElementCollection(targetClass = String.class)
    private List<String>imageUrls = new ArrayList<>();

    public Property() {
    }

    public Property(PropertyForm propertyForm) {
        this.name = propertyForm.getName();
        this.numberOfRooms = propertyForm.getNumberOfRooms();
        this.price = propertyForm.getPrice();
        this.description = propertyForm.getDescription();
        this.isValid = true;
        this.imageUrls = propertyForm.getImageUrl();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public PropertyState getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(PropertyState propertyState) {
        this.propertyState = propertyState;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
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

    public UserProperty getOwner() {
        return owner;
    }

    public void setOwner(UserProperty owner) {
        this.owner = owner;
    }

    public double getLngCoord() {
        return lngCoord;
    }

    public void setLngCoord(double lngCoord) {
        this.lngCoord = lngCoord;
    }

    public double getLatCoord() {
        return latCoord;
    }

    public void setLatCoord(double latCoord) {
        this.latCoord = latCoord;
    }
}
