package com.progmasters.moovsmart.domain;

import com.progmasters.moovsmart.dto.PropertyForm;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "boolean default true")
    private boolean isValid;

    @ManyToOne
    private UserProperty user;

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

}
