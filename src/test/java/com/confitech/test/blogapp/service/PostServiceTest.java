package com.confitech.test.blogapp.service;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.confitech.test.blogapp.entity.Post;
import com.confitech.test.blogapp.exception.ResourceNotFoundException;
import com.confitech.test.blogapp.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        post = new Post();
        post.setId(5L);
        post.setTitle("Test Title");
        post.setMessage("Test Message");
        post.setCategory("Test Category");
        post.setCreatedAt(now().minusDays(1));
        post.setUpdatedAt(now());
    }

    @Test
    public void testFindById_Success() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Post foundPost = postService.findById(1L);

        assertNotNull(foundPost);
        assertEquals(post.getTitle(), foundPost.getTitle());
        assertEquals(post.getMessage(), foundPost.getMessage());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.findById(1L));

        assertEquals("Post not found with id 1", exception.getMessage());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        List<Post> posts = List.of(post);
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> foundPosts = postService.findAll();

        assertEquals(1, foundPosts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post savedPost = postService.save(post);

        assertNotNull(savedPost);
        assertEquals(post.getTitle(), savedPost.getTitle());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testDeleteById_Success() {
        when(postRepository.existsById(anyLong())).thenReturn(true);

        postService.deleteById(1L);

        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_NotFound() {
        when(postRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.deleteById(1L));

        assertEquals("Post not found with id 1", exception.getMessage());
        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testUpdatePost_Success() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updtedPost = new Post();
        updtedPost.setId(1L);
        updtedPost.setTitle("Updated Title");

        Post updatedPost = postService.updatePost(updtedPost);

        assertNotNull(updatedPost);
        assertEquals("Updated Title", updatedPost.getTitle());
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(this.post);
    }

    @Test
    public void testUpdatePost_NotFound() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        Post post = new Post();
        post.setId(1L);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(post));

        assertEquals("Post not found with id 1", exception.getMessage());
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(0)).save(any(Post.class));
    }

    @Test
    public void testFindPostsBeforeDate() {
        List<Post> posts = List.of(post);
        LocalDateTime date = now();
        when(postRepository.findByCreatedAtBefore(any(LocalDateTime.class))).thenReturn(posts);

        List<Post> foundPosts = postService.findPostsBeforeDate(date);

        assertNotNull(foundPosts);
        assertEquals(1, foundPosts.size());
        verify(postRepository, times(1)).findByCreatedAtBefore(date);
    }
}