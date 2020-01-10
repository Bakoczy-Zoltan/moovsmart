package com.progmasters.moovsmart.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser {

    private List<String> role;
    private String name;
    private Long id;

    public AuthenticatedUser(UserDetails user) {
        this.role = getAllRoles(user.getAuthorities());
        this.name = user.getUsername();
    }

    private List<String> getAllRoles(Collection<? extends GrantedAuthority> authorities) {
        List<String>role = new ArrayList<>();
        for(GrantedAuthority authority: authorities){
            role.add(authority.getAuthority());
        }
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
