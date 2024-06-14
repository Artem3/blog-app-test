package com.confitech.test.blogapp.service;

import static java.util.stream.Collectors.toList;

import com.confitech.test.blogapp.dto.PostDTO;
import com.confitech.test.blogapp.entity.Post;
import com.confitech.test.blogapp.exception.ResourceNotFoundException;
import com.confitech.test.blogapp.mapper.PostMapper;
import com.confitech.test.blogapp.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostDTO> findAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(postMapper::toDTO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PostDTO findById(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        return postMapper.toDTO(post);
    }

    @Transactional(readOnly = true)
    public List<PostDTO> findPostsBeforeDate(LocalDateTime date) {
        return postRepository.findByCreatedAtBefore(date)
                .stream()
                .map(postMapper::toDTO)
                .collect(toList());
    }

    @Transactional
    public PostDTO save(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        post.setId(null);
        Post savedPost = postRepository.save(post);
        return postMapper.toDTO(savedPost);
    }

    @Transactional
    public PostDTO updatePost(PostDTO postDTO) {
        Long id = postDTO.getId();
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null for update");
        }
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        existingPost.setTitle(postDTO.getTitle());
        existingPost.setMessage(postDTO.getMessage());
        existingPost.setCategory(postDTO.getCategory());

        Post updatedPost = postRepository.save(existingPost);
        return postMapper.toDTO(updatedPost);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id " + id);
        }
        postRepository.deleteById(id);
    }
}
