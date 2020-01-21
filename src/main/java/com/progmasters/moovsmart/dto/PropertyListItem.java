package com.progmasters.moovsmart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.moovsmart.domain.Property;

import java.time.LocalDateTime;
import java.util.List;

public class PropertyListItem {

    private Long id;
    private String name;
    private Integer numberOfRooms;
    private Integer price;
    private List<String> imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime time;
    private Long userId;
    private Double area;
    private String status;

    public PropertyListItem() {
    }

    public PropertyListItem(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.numberOfRooms = property.getNumberOfRooms();
        this.price = property.getPrice();
        this.imageUrl = property.getImageUrls();
        this.time = property.getLocalDateTime();
        this.area = property.getArea();
        this.status = property.getStatus().getDisplayName();
        if(property.getOwner() != null){
            this.userId = property.getOwner().getId();
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
