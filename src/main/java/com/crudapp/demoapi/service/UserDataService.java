package com.crudapp.demoapi.service;

import com.crudapp.demoapi.model.Post;
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
        Users userList = userRepository.getByUserName(userName);
        return Optional.of(userList).orElse(null);
    }

    public void getPosts(String id) {
        Optional<Users> posts = userRepository.findById(Long.valueOf(id));
        System.out.println(posts.get().getPosts());
    }
}

