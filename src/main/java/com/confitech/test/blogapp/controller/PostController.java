package com.confitech.test.blogapp.controller;

import com.confitech.test.blogapp.dto.CreatePostDTO;
import com.confitech.test.blogapp.dto.PostDTO;
import com.confitech.test.blogapp.dto.UpdatePostDTO;
import com.confitech.test.blogapp.service.PostService;
import com.confitech.test.blogapp.util.DateTimeUtil;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping("/before/{date}")
    public List<PostDTO> getPostsBeforeDate(@PathVariable String date) {
        LocalDateTime dateTime = DateTimeUtil.parseDateTime(date);
        return postService.findPostsBeforeDate(dateTime);
    }

    @PostMapping
    public PostDTO createPost(@Valid @RequestBody CreatePostDTO postDTO) {
        return postService.save(postDTO);
    }

    @PutMapping
    public PostDTO updatePost(@Valid @RequestBody UpdatePostDTO postDTO) {
        return postService.updatePost(postDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deleteById(id);
    }
}
