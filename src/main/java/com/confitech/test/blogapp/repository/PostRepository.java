package com.confitech.test.blogapp.repository;

import com.confitech.test.blogapp.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"comments"})
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"comments"})
    List<Post> findAll();

    @EntityGraph(attributePaths = {"comments"})
    List<Post> findByCreatedAtBefore(LocalDateTime createdBefore);
}
