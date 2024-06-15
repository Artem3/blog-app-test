package com.confitech.test.blogapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.confitech.test.blogapp.dto.comment.CommentDTO;
import com.confitech.test.blogapp.dto.comment.CreateCommentDTO;
import com.confitech.test.blogapp.dto.comment.UpdateCommentDTO;
import com.confitech.test.blogapp.entity.Comment;
import com.confitech.test.blogapp.entity.Post;
import com.confitech.test.blogapp.repository.CommentRepository;
import com.confitech.test.blogapp.repository.PostRepository;
import com.confitech.test.blogapp.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestCommentController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Post post;
    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    public void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setMessage("Test Message");

        comment = new Comment();
        comment.setId(1L);
        comment.setMessage("Test Comment");
        comment.setPost(post);

        commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMessage("Test Comment");
        commentDTO.setPostId(1L);
    }

    @Test
    public void getCommentById() throws Exception {
        Mockito.when(commentService.getCommentById(anyLong())).thenReturn(commentDTO);

        mockMvc.perform(get("/api/v1/comments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Test Comment"))
                .andExpect(jsonPath("$.postId").value(1L));
    }

    @Test
    public void createComment() throws Exception {
        CreateCommentDTO createCommentDTO = new CreateCommentDTO();
        createCommentDTO.setPostId(1L);
        createCommentDTO.setMessage("Test Comment");

        Mockito.when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        Mockito.when(commentService.createComment(any(CreateCommentDTO.class))).thenReturn(commentDTO);

        mockMvc.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Test Comment"))
                .andExpect(jsonPath("$.postId").value(1L));
    }

    @Test
    public void updateComment() throws Exception {
        UpdateCommentDTO commentToUpdate = new UpdateCommentDTO();
        commentToUpdate.setId(1L);
        commentToUpdate.setPostId(1L);
        commentToUpdate.setMessage("Updated Comment");

        CommentDTO updatedCommentDTO = new CommentDTO();
        updatedCommentDTO.setId(1L);
        updatedCommentDTO.setMessage("Updated Comment");
        updatedCommentDTO.setPostId(1L);

        Mockito.when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        Mockito.when(commentService.updateComment(any(UpdateCommentDTO.class))).thenReturn(updatedCommentDTO);

        mockMvc.perform(put("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Updated Comment"))
                .andExpect(jsonPath("$.postId").value(1L));
    }
}