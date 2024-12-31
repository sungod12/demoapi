package com.crudapp.demoapi.service;

import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.model.Post;
import com.crudapp.demoapi.model.Users;
import com.crudapp.demoapi.repository.PostRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import org.hibernate.query.Order;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class PostDataService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private UserDataService userDataService;


    public PostDTO createPost(PostDTO postDTO){
        Users retrievedUser = userDataService.getUserDetails();
        Post post=mapperService.getPost(postDTO);
        post.setUser(retrievedUser);
        post.setCreated(Timestamp.from(Instant.now()));
        Post savedPost=postRepository.save(post);
        return mapperService.getPostDTO(savedPost);
    }

    public List<PostDTO> getPostDTOs(String sortBy,String orderBy){
        Sort sort= Sort.by(Sort.Direction.fromString(orderBy),sortBy);
        Users retrievedUser = userDataService.getUserDetails();
        List<Post> posts=postRepository.getPostByUserId(retrievedUser.getId(),sort);
        if(posts.isEmpty()){
            return Collections.emptyList();
        }
        return mapperService.getPostsDTOs(posts);
    }
}
