package com.crudapp.demoapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String role;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.MERGE)
    private Set<Users> users;
}
