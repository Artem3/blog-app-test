package com.confitech.test.blogapp.service;

import com.confitech.test.blogapp.dto.comment.CommentDTO;
import com.confitech.test.blogapp.dto.comment.CreateCommentDTO;
import com.confitech.test.blogapp.dto.comment.UpdateCommentDTO;
import com.confitech.test.blogapp.entity.Comment;
import com.confitech.test.blogapp.entity.Post;
import com.confitech.test.blogapp.exception.ResourceNotFoundException;
import com.confitech.test.blogapp.mapper.CommentMapper;
import com.confitech.test.blogapp.repository.CommentRepository;
import com.confitech.test.blogapp.repository.PostRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        return commentMapper.toDTO(comment);
    }

    @Transactional
    public CommentDTO createComment(CreateCommentDTO createCommentDTO) {
        Post post = postRepository.findById(createCommentDTO.getPostId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post not found with id " + createCommentDTO.getPostId()));
        Comment comment = commentMapper.toEntity(createCommentDTO);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toDTO(savedComment);
    }

    @Transactional
    public CommentDTO updateComment(UpdateCommentDTO updateCommentDTO) {
        Post existingPost = postRepository.findById(updateCommentDTO.getPostId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post not found with id " + updateCommentDTO.getPostId()));

        boolean commentBelongsToPost = existingPost.getComments().stream()
                .map(Comment::getId)
                .collect(Collectors.toSet())
                .contains(updateCommentDTO.getId());

        if (!commentBelongsToPost) {
            throw new ResourceNotFoundException(
                    "Comment not found with id " + updateCommentDTO.getId() + " for post with id " +
                    updateCommentDTO.getPostId());
        }

        Comment existingComment = commentRepository.findById(updateCommentDTO.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Comment not found with id " + updateCommentDTO.getId()));

        existingComment.setMessage(updateCommentDTO.getMessage());
        existingComment.setPost(existingPost);

        Comment updatedComment = commentRepository.save(existingComment);
        return commentMapper.toDTO(updatedComment);
    }
}