package com.crudapp.demoapi.service;

import com.crudapp.demoapi.model.Users;
import com.crudapp.demoapi.repository.RolesRepository;
import com.crudapp.demoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // JPA repository for 'Users'

    @Autowired
    private RolesRepository roleRepository; // JPA repository for 'Roles'


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from your users table
        Users user = userRepository.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Collect all authorities (roles) for the user
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase()))
                .collect(Collectors.toSet());


        // Return a Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                true, // assuming you have an 'enabled' field in your 'Users' entity
                true, true, true, // account non-expired, credentials non-expired, account non-locked
                authorities
        );
    }
}

