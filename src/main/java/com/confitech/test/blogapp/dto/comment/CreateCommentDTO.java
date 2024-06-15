package com.confitech.test.blogapp.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentDTO {

    @NotBlank(message = "Comment message is mandatory")
    private String message;

    @NotNull(message = "Post ID is mandatory")
    private Long postId;
}