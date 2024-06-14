package com.confitech.test.blogapp.mapper;

import com.confitech.test.blogapp.dto.PostDTO;
import com.confitech.test.blogapp.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        if (post == null) {
            return null;
        }
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setMessage(post.getMessage());
        dto.setCategory(post.getCategory());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        return dto;
    }

    public Post toEntity(PostDTO dto) {
        if (dto == null) {
            return null;
        }
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setMessage(dto.getMessage());
        post.setCategory(dto.getCategory());

        return post;
    }
}