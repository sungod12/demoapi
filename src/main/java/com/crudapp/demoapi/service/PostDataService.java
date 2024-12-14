package com.crudapp.demoapi.service;

import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.model.Post;
import com.crudapp.demoapi.repository.PostRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostDataService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MapperService mapperService;


    //TODO- Provide sortDirection option
    public List<PostDTO> getPostDTOs(Long userId, String sortBy){
        Sort sort= StringUtils.isNotEmpty(sortBy) && sortBy.equalsIgnoreCase("created")?Sort.by("created").descending():Sort.by("updated");
        List<Post> posts=postRepository.getPostByUserId(userId,sort);
        if(posts.isEmpty()){
            return Collections.emptyList();
        }
        List<PostDTO> postDTOs=mapperService.getPostsDTOs(posts);
        return postDTOs;
    }
}
