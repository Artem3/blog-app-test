package com.confitech.test.blogapp.mapper;

import com.confitech.test.blogapp.dto.comment.CommentDTO;
import com.confitech.test.blogapp.dto.comment.CreateCommentDTO;
import com.confitech.test.blogapp.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setMessage(comment.getMessage());
        dto.setPostId(comment.getPost().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    public Comment toEntity(CreateCommentDTO dto) {
        if (dto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setMessage(dto.getMessage());
        return comment;
    }
}