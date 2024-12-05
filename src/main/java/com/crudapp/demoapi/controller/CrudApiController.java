package com.crudapp.demoapi.controller;

import com.crudapp.demoapi.model.CryptoDetails;
//import com.crudapp.demoapi.model.Users;
//import com.crudapp.demoapi.service.UserDataService;
import com.crudapp.demoapi.model.Role;
import com.crudapp.demoapi.model.Users;
import com.crudapp.demoapi.repository.RolesRepository;
import com.crudapp.demoapi.service.UserDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    private List<Role> roleList;

    private boolean isAuthenticatedUser = false;

    private Users retrievedUser;

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
    ResponseEntity<String> signIn(@RequestBody Users user) {
        retrievedUser = userDataService.getUserDetails(user.getUserName());
        if (Objects.isNull(retrievedUser)) {
            return new ResponseEntity<>("No such user found", HttpStatus.NOT_FOUND);
        } else {
            if (bCryptPasswordEncoder.matches(user.getPassword(), retrievedUser.getPassword())) {
                isAuthenticatedUser = true;
                return new ResponseEntity<>("Login Successful", HttpStatus.OK);
            } else {
                isAuthenticatedUser = false;
                retrievedUser = null;
                return new ResponseEntity<>("Please check your password", HttpStatus.BAD_REQUEST);
            }
        }
    }

     /*
    @PutMapping("/updateDetails")
    ResponseEntity<String> updateDetails(@RequestBody Users user) {
        if (isAuthenticatedUser) {
            retrievedUser.setFirstName(user.getFirstName());
            retrievedUser.setLastName(user.getLastName());
            retrievedUser.setPhoneNumber(user.getPhoneNumber());
            retrievedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDataService.saveUserDetails(retrievedUser);
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized Users", HttpStatus.FORBIDDEN);
        }
    }*/

    @GetMapping("/login")
    ResponseEntity<String> signIn() {
        return ResponseEntity.ok("login successful");
    }

    // doesn't work due to either api key or some other thing//
    @GetMapping("/getCryptoDetails")
    ResponseEntity<String> getCryptoDetails(@RequestParam String symbol) throws JsonProcessingException {
        /*RestTemplate restTemplate = new RestTemplate();


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-CMC_PRO_API_KEY", "27ab17d1-215f-49e5-9ca4-afd48810c149");

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> coinApiResponse = restTemplate.exchange(
                "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol={symbol}", HttpMethod.GET, requestEntity, String.class,symbol);*/

        WebClient webClient = WebClient.builder().baseUrl("https://pro-api.coinmarketcap.com").build();

        String url = "/v1/cryptocurrency/quotes/latest";
        Mono<String> response = webClient.get().uri(uriBuilder -> uriBuilder.path(url).queryParam("symbol", symbol).build()).header("X-CMC_PRO_API_KEY", "27ab17d1-215f-49e5-9ca4-afd48810c149").
                retrieve().bodyToMono(String.class).flatMap(Mono::just);

        log.info("param got is {}", symbol);

        CryptoDetails cryptoDetails = new ObjectMapper().readValue(response.block(), CryptoDetails.class);

        System.out.println(new Gson().toJson(cryptoDetails.getData()));
        return new ResponseEntity<>(response.block(), HttpStatus.OK);
    }

}