package com.confitech.test.blogapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.confitech.test.blogapp.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApplicationTests {

    @Autowired
    private PostService postService;

    @Test
    void contextLoads() {
        assertNotNull(postService);
    }

}
