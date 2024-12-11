package com.crudapp.demoapi.dto;

import com.crudapp.demoapi.model.Users;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDTO {
    private String userId;

    private Long postId;

    private String content;

    private String created;

    private String updated;


}
