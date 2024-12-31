package com.crudapp.demoapi.service;

import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.dto.UserDTO;
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

    @Autowired
    private MapperService mapperService;

    private List<Role> roles;

    @PostConstruct
    private void getRolesList() {
        roles = rolesRepository.findAll();
    }

    public UserDTO registerUser(UserDTO userDTO) {
        Users user= mapperService.getUsers(userDTO);
        String encodedPassword=bCryptPasswordEncoder.encode(userDTO.getPassword());
        user.setRoles(roles.stream().filter(role -> role.getRole().equalsIgnoreCase("user")).collect(Collectors.toSet()));
        user.setPassword(encodedPassword);
        Users registeredUser=userRepository.save(user);
        registeredUser.setPassword(null);
        return mapperService.getUserDTO(registeredUser);
    }

    public UserDTO updateUserDetails(UserDTO user){
        Users retrievedUser=getUserDetails();
        retrievedUser.setUserName(user.getUserName());
        retrievedUser.setFullName(user.getFullName());
        retrievedUser.setPhoneNumber(user.getPhoneNumber());
        retrievedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Users updatedUser=userRepository.save(retrievedUser);
        updatedUser.setPassword(null);
        return mapperService.getUserDTO(updatedUser);
    }

    public Users getUserDetails() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userRepository.getByUserName(authentication.getName());
        return Optional.of(retrievedUser).orElse(null);
    }
}

