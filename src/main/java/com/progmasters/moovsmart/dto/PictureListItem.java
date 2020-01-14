package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.Property;

import java.util.List;

public class PictureListItem {

    private List<String> imageUrl;
    private List<String> publicId;

    public PictureListItem() {
    }

    public PictureListItem(Property property) {
        this.imageUrl = property.getImageUrls();
        this.publicId = property.getPublicIds();
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getPublicId() {
        return publicId;
    }

    public void setPublicId(List<String> publicId) {
        this.publicId = publicId;
    }
}
