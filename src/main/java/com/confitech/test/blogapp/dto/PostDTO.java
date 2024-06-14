package com.confitech.test.blogapp.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String message;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
