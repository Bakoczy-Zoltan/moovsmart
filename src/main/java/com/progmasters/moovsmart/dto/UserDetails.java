package com.progmasters.moovsmart.dto;

import com.progmasters.moovsmart.domain.UserProperty;

public class UserDetails {

    private Long id;
    private String mail;
    private String userName;
    private Boolean isActive;

    public UserDetails() {
    }

    public UserDetails(UserProperty userProperty) {
        this.id = userProperty.getId();
        this.mail = userProperty.getMail();
        this.userName = userProperty.getUserName();
        this.isActive = userProperty.getIsActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
