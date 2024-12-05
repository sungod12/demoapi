package com.crudapp.demoapi.service;

import com.crudapp.demoapi.model.Users;
import com.crudapp.demoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService {
    @Autowired
    private UserRepository userRepository;

    public void saveUserDetails(Users user) {
        userRepository.save(user);
    }

    public Users getUserDetails(String userName) {
        List<Users> userList = userRepository.getByUserName(userName);
        Optional<Users> optionalUser = userList.stream().filter(user1 -> user1.getUserName().equals(userName)).findFirst();
        return optionalUser.orElse(null);
    }
}

