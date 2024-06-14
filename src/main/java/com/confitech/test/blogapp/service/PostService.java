package com.confitech.test.blogapp.service;

import com.confitech.test.blogapp.entity.Post;
import com.confitech.test.blogapp.exception.ResourceNotFoundException;
import com.confitech.test.blogapp.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<Post> findPostsBeforeDate(LocalDateTime date) {
        return postRepository.findByCreatedAtBefore(date);
    }

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Post postToUpdate) {
        Post post = postRepository.findById(postToUpdate.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postToUpdate.getId()));

        post.setTitle(postToUpdate.getTitle());
        post.setMessage(postToUpdate.getMessage());
        post.setCategory(postToUpdate.getCategory());

        return postRepository.save(post);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id " + id);
        }
        postRepository.deleteById(id);
    }
}
