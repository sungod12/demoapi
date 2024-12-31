package com.crudapp.demoapi.service;

import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.dto.UserDTO;
import com.crudapp.demoapi.model.Post;
import com.crudapp.demoapi.model.Users;
import org.h2.engine.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapperService {

    ModelMapper modelMapper=new ModelMapper();

    public PostDTO getPostDTO(Post post){
        return modelMapper.map(post, PostDTO.class);
    }

    public List<PostDTO> getPostsDTOs(List<Post> posts) {
        return posts.stream().map(this::getPostDTO).toList();
    }

    public Post getPost(PostDTO postDTO){
        return modelMapper.map(postDTO,Post.class);
    }

    public Users getUsers(UserDTO userDTO){
        return modelMapper.map(userDTO, Users.class);
    }

    public UserDTO getUserDTO(Users user){
        return modelMapper.map(user, UserDTO.class);
    }
}
