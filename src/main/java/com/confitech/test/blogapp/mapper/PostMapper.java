package com.confitech.test.blogapp.mapper;

import static java.util.stream.Collectors.toList;

import com.confitech.test.blogapp.dto.post.CreatePostDTO;
import com.confitech.test.blogapp.dto.post.PostDTO;
import com.confitech.test.blogapp.entity.Post;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final CommentMapper commentMapper;

    public PostMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

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
        if (post.getComments() != null) {
            dto.setComments(post.getComments().stream()
                    .map(commentMapper::toDTO)
                    .collect(toList()));
        } else {
            dto.setComments(List.of());
        }
        return dto;
    }

    public Post toEntity(CreatePostDTO dto) {
        if (dto == null) {
            return null;
        }
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setMessage(dto.getMessage());
        post.setCategory(dto.getCategory());
        return post;
    }

}