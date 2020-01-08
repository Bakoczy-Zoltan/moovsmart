package com.progmasters.moovsmart.security;

import com.progmasters.moovsmart.domain.UserProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "active_token")
public class TokenStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activeToken;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private UserProperty tokenUser;

    @Column(name = "token_generating_date")
    private LocalDateTime date;

    public TokenStorage() {
    }

    public TokenStorage(String activeToken, UserProperty tokenUser, LocalDateTime date) {
        this.activeToken = activeToken;
        this.tokenUser = tokenUser;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }

    public UserProperty getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(UserProperty tokenUser) {
        this.tokenUser = tokenUser;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
