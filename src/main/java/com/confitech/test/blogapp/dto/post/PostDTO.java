package com.confitech.test.blogapp.dto.post;

import com.confitech.test.blogapp.dto.comment.CommentDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String message;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDTO> comments;
}
