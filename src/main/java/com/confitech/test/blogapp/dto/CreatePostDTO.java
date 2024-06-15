package com.confitech.test.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Message is mandatory")
    private String message;

    private String category;
}