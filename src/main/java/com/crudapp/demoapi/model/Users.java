package com.crudapp.demoapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{10}",message = "Phone number must contain 10 digits")
    private String phoneNumber;

    @Column(unique = true)
    @JsonAlias(value="username")
    @Size(min = 4,max=15,message = "Please enter valid username")
    private String userName;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).*$",message = "Password must contain atleast one uppercase,one lowercase,one digit and one special character")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;
}
