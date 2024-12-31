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
    private String phoneNumber;

    @Column(unique = true)
    private String userName;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;
}
