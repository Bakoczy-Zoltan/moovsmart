package com.progmasters.moovsmart.security;

import java.time.LocalDateTime;

public class CreateValidUserCommand {

    private Long id;
    private String userToken;
    private LocalDateTime date;

    public CreateValidUserCommand() {
    }

    public CreateValidUserCommand(Long id, String userToken, LocalDateTime date) {
        this.id = id;
        this.userToken = userToken;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
