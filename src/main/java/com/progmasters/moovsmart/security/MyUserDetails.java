package com.progmasters.moovsmart.security;

import com.progmasters.moovsmart.domain.RoleType;
import com.progmasters.moovsmart.domain.UserProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private List<GrantedAuthority> roleList;
    private boolean isActive;

    public MyUserDetails() {
    }

    public MyUserDetails(UserProperty user) {
        this.userName = user.getMail();
        this.password = user.getPassword();
        this.roleList = makeRoles(user.getRoleTypes());
        this.isActive = user.getIsActive();
    }

    private List<GrantedAuthority> makeRoles(List<RoleType> roleTypes) {
        List<GrantedAuthority> roles = new ArrayList<>();
        for (RoleType role : roleTypes) {
            roles.add(new SimpleGrantedAuthority(role.toString()));
        }
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<GrantedAuthority> roleList) {
        this.roleList = roleList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
