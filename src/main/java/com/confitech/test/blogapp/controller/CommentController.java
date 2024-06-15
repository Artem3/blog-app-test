package com.confitech.test.blogapp.controller;

import com.confitech.test.blogapp.dto.comment.CommentDTO;
import com.confitech.test.blogapp.dto.comment.CreateCommentDTO;
import com.confitech.test.blogapp.dto.comment.UpdateCommentDTO;
import com.confitech.test.blogapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public CommentDTO createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {
        return commentService.createComment(createCommentDTO);
    }

    @PutMapping
    public CommentDTO updateComment(@Valid @RequestBody UpdateCommentDTO updateCommentDTO) {
        return commentService.updateComment(updateCommentDTO);
    }
}