package com.crudapp.demoapi.controller;

import com.crudapp.demoapi.model.User;
import com.crudapp.demoapi.service.UserDataService;
import com.crudapp.demoapi.service.ValidatorService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api")
class CrudApiController {
    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private boolean isAuthenticatedUser = false;

    private User retrievedUser;

    @PostMapping("/register")
    ResponseEntity<String> signUp(@RequestBody User user) {
        boolean isValidUsername = validatorService.isValidUserName(user.getUserName());
        boolean isValidPassword = validatorService.isValidPassword(user.getPassword());
        if (isValidUsername && isValidPassword) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userDataService.saveUserDetails(user);
            return new ResponseEntity<>("SignUp Successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid username/password", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    ResponseEntity<String> signIn(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUserName())) {
            return new ResponseEntity<>("Please enter valid username", HttpStatus.BAD_REQUEST);
        }
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

    @PutMapping("/updateDetails")
    ResponseEntity<String> updateDetails(@RequestBody User user) {
        if (isAuthenticatedUser) {
            retrievedUser.setFirstName(user.getFirstName());
            retrievedUser.setLastName(user.getLastName());
            retrievedUser.setPhoneNumber(user.getPhoneNumber());
            retrievedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDataService.saveUserDetails(retrievedUser);
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized User", HttpStatus.FORBIDDEN);
        }
    }

    // doesn't work due to either api key or some other thing//
   /* @GetMapping("/getCryptoDetails")
    ResponseEntity<String> getCryptoDetails(){
        if(isAuthenticatedUser){
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://proapi.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC");

            // Make the API call
            String response = restTemplate.getForObject(uriBuilder.toUriString(), Map.class).toString();

            return new ResponseEntity<>("ok",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Unauthorized User", HttpStatus.FORBIDDEN);
        }
    }*/

}