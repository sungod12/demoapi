package com.crudapp.demoapi.service;

import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.model.Post;
import com.crudapp.demoapi.model.Role;
import com.crudapp.demoapi.model.Users;
import com.crudapp.demoapi.repository.RolesRepository;
import com.crudapp.demoapi.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDataService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private List<Role> roles;

    @PostConstruct
    private void getRolesList() {
        roles = rolesRepository.findAll();
    }

    public void registerUser(Users user) {
        String encodedPassword=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(roles.stream().filter(role -> role.getRole().equalsIgnoreCase("user")).collect(Collectors.toSet()));
        userRepository.save(user);
    }

    public void updateUserDetails(Users user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = this.getUserDetails(authentication.getName());
        retrievedUser.setUserName(user.getUserName());
        retrievedUser.setFullName(user.getFullName());
        retrievedUser.setPhoneNumber(user.getPhoneNumber());
        retrievedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(retrievedUser);

    }

    public Users getUserDetails(String userName) {
        Users userList = userRepository.getByUserName(userName);
        return Optional.of(userList).orElse(null);
    }
}

