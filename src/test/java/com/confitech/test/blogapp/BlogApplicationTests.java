package com.confitech.test.blogapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.confitech.test.blogapp.controller.PostController;
import com.confitech.test.blogapp.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private PostController postController;
    @Autowired
    private PostService postService;

    @Test
    void contextLoads() {
        assertNotNull(postController);
        assertNotNull(postService);
    }

}
