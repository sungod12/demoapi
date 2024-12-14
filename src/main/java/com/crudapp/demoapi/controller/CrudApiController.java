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
import com.crudapp.demoapi.service.PostDataService;
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
    private PostDataService postDataService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MapperService mapperService;

    //TODO - Refactor this to have code in userDataService - Done
    @PostMapping("/register")
    ResponseEntity<String> signUp(@RequestBody @Valid Users user) {
        userDataService.registerUser(user);
        return new ResponseEntity<>("SignUp Successful", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<String> signIn() {
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    //TODO - Refactor this to have code in userDataService - Done
    @PutMapping("/updateDetails")
    ResponseEntity<String> updateUserDetails(@RequestBody @Valid Users user, HttpServletRequest request) {
        userDataService.updateUserDetails(user);
        return new ResponseEntity<>("Update successful", HttpStatus.OK);
    }

    //TODO - Refactor this to have code in postDataService
    @PostMapping("/createPost")
    ResponseEntity<PostDTO> createPost(@RequestBody Post post){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userDataService.getUserDetails(authentication.getName());
        post.setUser(retrievedUser);
        post.setCreated(Timestamp.from(Instant.now()));
        postRepository.save(post);
        PostDTO postDTO=mapperService.getPostDTO(post);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getPosts")
    ResponseEntity<Object> getPosts(@RequestParam(required = false) String sortBy){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userDataService.getUserDetails(authentication.getName());
        List<PostDTO> postDTOs=postDataService.getPostDTOs(retrievedUser.getId(),sortBy);
        return new ResponseEntity<>(postDTOs,HttpStatus.OK);
    }

    @PutMapping("/updatePost")
    ResponseEntity<String> updatePost(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users retrievedUser = userDataService.getUserDetails(authentication.getName());
//        Users retrievedUser = postRepository.findById(authentication.getName());
//        post.setUser(retrievedUser);
//        post.setCreated(Timestamp.from(Instant.now()));
//        postRepository.save(post);
        return new ResponseEntity<>("Post updated", HttpStatus.OK);
    }




}