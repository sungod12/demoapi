package com.crudapp.demoapi.service;

import com.crudapp.demoapi.dto.PostDTO;
import com.crudapp.demoapi.model.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    public PostDTO getPostDTO(Post post){
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Post, PostDTO>() {

            @Override
            protected void configure() {
                map(source.getPost(),destination.getContent());
                map(source.getUser().getId(),destination.getUserId());
            }
        });
        return modelMapper.map(post, PostDTO.class);
    }
}
