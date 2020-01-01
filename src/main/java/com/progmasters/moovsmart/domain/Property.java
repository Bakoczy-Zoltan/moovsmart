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


    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @Min(value = 1)
    @Max(value = 12)
    private Integer numberOfRooms;

    private Integer price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isValid;

    @OneToMany(mappedBy = "property")
    private List<ImageProperty> imageUrls = new ArrayList<>();

    public Property() {
    }

    public Property(PropertyForm propertyForm) {
        this.name = propertyForm.getName();
        this.numberOfRooms = propertyForm.getNumberOfRooms();
        this.price = propertyForm.getPrice();
        this.description = propertyForm.getDescription();
        this.isValid = true;
        this.imageUrls = makeImageList(propertyForm.getImageUrl());
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

    public List<ImageProperty> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageProperty> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ImageProperty> makeImageList(List<String>urls){
        List<ImageProperty>imgUrls = new ArrayList<>();
        for(String url : urls){
            imgUrls.add(new ImageProperty(url));
        }
        return imgUrls;
    }

}
