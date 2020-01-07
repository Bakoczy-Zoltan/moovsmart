package com.progmasters.moovsmart.security;


import com.progmasters.moovsmart.domain.RoleType;
import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProperty> user = this.userRepository.findUserPropertiesByMail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username + " is not found");
        }
            UserProperty realUser = user.get();

           // List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(realUser.getRoleTypes().toString());

            List<GrantedAuthority> authorities = new ArrayList<>();
            for(RoleType role: realUser.getRoleTypes()){
                authorities.add(new SimpleGrantedAuthority(role.toString()));
            }

            UserDetails principal = org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .authorities(authorities)
                    .password(realUser.getPassword())
                    .build();

        return principal;
    }
}
