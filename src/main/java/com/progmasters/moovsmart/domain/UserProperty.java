package com.progmasters.moovsmart.domain;

import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.security.TokenStorage;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Email(message = "Email should be valid")
    private String mail;

    @Pattern(regexp="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")
    private String password;

    private boolean isActive;

    @OneToOne(mappedBy = "tokenUser")
    private TokenStorage tokenStorage;

    @OneToMany(mappedBy = "owner")
    private List<Property> properties = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    @Column(name = "user_role")
    private List<RoleType> roleTypes = new ArrayList<>();


    public UserProperty() {
    }

    public UserProperty(CreateUserCommand command) {
        this.userName = command.getUserName();
        this.mail = command.getMail();
        this.isActive = false;
        this.roleTypes = makeRoles();
    }

    private List<RoleType> makeRoles() {
        List<RoleType> roles = new ArrayList<>();
        roles.add(RoleType.valueOf("ROLE_USER"));
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<RoleType> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(List<RoleType> roleTypes) {
        this.roleTypes = roleTypes;
    }

    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    public void setTokenStorage(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }
}
