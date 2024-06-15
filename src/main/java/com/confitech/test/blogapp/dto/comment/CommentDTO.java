package com.confitech.test.blogapp.dto.comment;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;
    private String message;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}