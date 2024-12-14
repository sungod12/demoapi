package com.crudapp.demoapi.repository;

import com.crudapp.demoapi.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    public List<Post> getPostByUserId(Long id, Sort sort);

}
