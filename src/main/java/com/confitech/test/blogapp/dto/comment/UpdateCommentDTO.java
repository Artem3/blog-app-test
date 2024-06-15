package com.confitech.test.blogapp.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCommentDTO {

    @NotNull(message = "Comment ID is mandatory")
    private Long id;

    @NotNull(message = "Post ID is mandatory")
    private Long postId;

    @NotBlank(message = "Comment message is mandatory")
    private String message;
}