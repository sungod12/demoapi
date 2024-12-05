package com.crudapp.demoapi.repository;

import com.crudapp.demoapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
}
