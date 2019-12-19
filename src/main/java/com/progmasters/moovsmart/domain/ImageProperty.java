package com.progmasters.moovsmart.domain;

import javax.persistence.*;

@Entity
@Table(name = "img")
public class ImageProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    private Property property;

    public ImageProperty() {
    }

    public ImageProperty(String url) {
        this.url = url;
    }

    public ImageProperty(String url, Property property) {
        this.url = url;
        this.property = property;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
