package com.crudapp.demoapi.controller;

//import com.crudapp.demoapi.model.Users;
//import com.crudapp.demoapi.service.UserDataService;
import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.model.Post;
import com.crudapp.demoapi.model.Role;
import com.crudapp.demoapi.model.Users;
import com.crudapp.demoapi.repository.PostRepository;
import com.crudapp.demoapi.repository.RolesRepository;
import com.crudapp.demoapi.service.MapperService;
import com.crudapp.demoapi.service.UserDataService;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api")
class CrudApiController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MapperService mapperService;

    private List<Role> roleList;

    @PostConstruct
    private void getRolesList() {
        roleList = rolesRepository.findAll();
    }

    @PostMapping("/register")
    ResponseEntity<String> signUp(@RequestBody @Valid Users user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(roleList.stream().filter(role -> role.getRole().equalsIgnoreCase("user")).collect(Collectors.toSet()));
        userDataService.saveUserDetails(user);
        return new ResponseEntity<>("SignUp Successful", HttpStatus.CREATED);
    }


    @PostMapping("/login")
    ResponseEntity<String> signIn() {
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    @PutMapping("/updateDetails")
    ResponseEntity<String> updateUserDetails(@RequestBody @Valid Users user, HttpServletRequest request) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userDataService.getUserDetails(authentication.getName());
        retrievedUser.setUserName(user.getUserName());
        retrievedUser.setFullName(user.getFullName());
        retrievedUser.setPhoneNumber(user.getPhoneNumber());
        retrievedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDataService.saveUserDetails(retrievedUser);
        return new ResponseEntity<>("Update successful", HttpStatus.OK);
    }

    @PostMapping("/createPost")
    ResponseEntity<PostDTO> createPost(@RequestBody Post post){
        ModelMapper modelMapper=new ModelMapper();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userDataService.getUserDetails(authentication.getName());
        post.setUser(retrievedUser);
        post.setCreated(Timestamp.from(Instant.now()));
        postRepository.save(post);
        PostDTO postDTO=mapperService.getPostDTO(post);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping("/updatePost")
    ResponseEntity<String> updatePost(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userDataService.getUserDetails(authentication.getName());
        userDataService.getPosts(String.valueOf(retrievedUser.getId()));
//        Users retrievedUser = postRepository.findById(authentication.getName());
//        post.setUser(retrievedUser);
//        post.setCreated(Timestamp.from(Instant.now()));
//        postRepository.save(post);
        return new ResponseEntity<>("Post updated", HttpStatus.OK);
    }




}