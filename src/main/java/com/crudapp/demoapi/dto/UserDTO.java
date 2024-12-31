package com.crudapp.demoapi.dto;

import com.crudapp.demoapi.model.Post;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;

    @NotBlank
    private String fullName;

    @Pattern(regexp = "^[0-9]{10}",message = "Phone number must contain 10 digits")
    private String phoneNumber;

    @JsonAlias(value="username")
    @Size(min = 4,max=15,message = "Please enter valid username")
    private String userName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).*$",message = "Password must contain atleast one uppercase,one lowercase,one digit and one special character")
    private String password;

    private Set<Post> posts=new HashSet<>();

}
