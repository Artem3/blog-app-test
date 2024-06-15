package com.confitech.test.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePostDTO {

    @NotNull(message = "ID is mandatory")
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Message is mandatory")
    private String message;

    private String category;
}