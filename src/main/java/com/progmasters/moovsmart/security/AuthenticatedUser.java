package com.progmasters.moovsmart.security;

import org.springframework.security.core.GrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser {

    private List<String> role;
    private String name;

    public AuthenticatedUser(MyUserDetails user) {
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


}
