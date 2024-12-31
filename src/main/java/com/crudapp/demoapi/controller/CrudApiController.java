package com.crudapp.demoapi.controller;

//import com.crudapp.demoapi.model.Users;
//import com.crudapp.demoapi.service.UserDataService;
import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.dto.UserDTO;
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
import org.springframework.security.core.userdetails.User;
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
    private MapperService mapperService;

    @PostMapping("/register")
    ResponseEntity<UserDTO> signUp(@RequestBody @Valid UserDTO user) {
        UserDTO registerUser=userDataService.registerUser(user);
        return new ResponseEntity<>(registerUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<String> signIn() {
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    @PutMapping("/updateDetails")
    ResponseEntity<UserDTO> updateUserDetails(@RequestBody @Valid UserDTO user, HttpServletRequest request) {
        UserDTO updatedUser=userDataService.updateUserDetails(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/createPost")
    ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post){
        PostDTO postDTO= postDataService.createPost(post);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getPosts")
    ResponseEntity<Object> getPosts(@RequestParam(required = false,defaultValue = "updated") String sortBy,@RequestParam(required = false,defaultValue = "desc") String orderBy){
        if(!orderBy.equalsIgnoreCase("desc") && !orderBy.equalsIgnoreCase("asc")){
            return ResponseEntity.badRequest().body(null);
        }
        List<PostDTO> postDTOs=postDataService.getPostDTOs(sortBy,orderBy);
        if(postDTOs.isEmpty()){
            return new ResponseEntity<>("No posts found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOs,HttpStatus.OK);
    }

    //TODO - Add post update logic
    @PutMapping("/updatePost")
    ResponseEntity<String> updatePost(){
//        Users retrievedUser = postRepository.findById(authentication.getName());
//        post.setUser(retrievedUser);
//        post.setCreated(Timestamp.from(Instant.now()));
//        postRepository.save(post);
        return new ResponseEntity<>("Post updated", HttpStatus.OK);
    }




}