package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.ImageProperty;
import com.progmasters.moovsmart.domain.Property;

import java.util.List;

public class PropertyListItem {

    private long id;
    private String name;
    private int numberOfRooms;
    private int price;
    private String imageUrl;

    public PropertyListItem() {
    }

    public PropertyListItem(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.numberOfRooms = property.getNumberOfRooms();
        this.price = property.getPrice();
        this.imageUrl = makeImageUrl(property.getImageUrls());
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String makeImageUrl(List<ImageProperty> urls) {
       String url = null;
       if(urls != null && !urls.isEmpty()){
           url = urls.get(0).getUrl();
       }
        return url;
    }
}
