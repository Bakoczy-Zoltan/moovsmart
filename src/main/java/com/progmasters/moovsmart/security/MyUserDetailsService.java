package com.progmasters.moovsmart.security;


import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return new MyUserDetails(realUser);
    }
}
