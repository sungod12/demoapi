package com.crudapp.demoapi.repository;

import com.crudapp.demoapi.model.Post;
import com.crudapp.demoapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users getByUserName(@Param("userName") String username);

}
