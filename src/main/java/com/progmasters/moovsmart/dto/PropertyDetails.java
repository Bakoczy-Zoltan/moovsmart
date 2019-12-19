package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.ImageProperty;
import com.progmasters.moovsmart.domain.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetails {

    private long id;
    private String name;
    private int numberOfRooms;
    private int price;
    private String description;
    private List<String> imageUrl;

    public PropertyDetails() {
    }

    public PropertyDetails(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.numberOfRooms = property.getNumberOfRooms();
        this.price = property.getPrice();
        this.description = property.getDescription();
        this.imageUrl = makeImageUrls(property.getImageUrls());
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

    public List<String> makeImageUrls(List<ImageProperty> urls) {
        List<String> urlsStrings = new ArrayList<>();
        for (ImageProperty img : urls) {
            urlsStrings.add(img.getUrl());
        }
        return urlsStrings;
    }
}
