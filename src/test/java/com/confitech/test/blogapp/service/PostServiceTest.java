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

import com.confitech.test.blogapp.dto.PostDTO;
import com.confitech.test.blogapp.entity.Post;
import com.confitech.test.blogapp.exception.ResourceNotFoundException;
import com.confitech.test.blogapp.mapper.PostMapper;
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

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostDTO postDTO;

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

        postDTO = new PostDTO();
        postDTO.setId(5L);
        postDTO.setTitle("Test Title");
        postDTO.setMessage("Test Message");
        postDTO.setCategory("Test Category");
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
    }

    @Test
    public void testFindById_Success() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO foundPostDTO = postService.findById(1L);

        assertNotNull(foundPostDTO);
        assertEquals(postDTO.getTitle(), foundPostDTO.getTitle());
        assertEquals(postDTO.getMessage(), foundPostDTO.getMessage());
        verify(postRepository, times(1)).findById(1L);
        verify(postMapper, times(1)).toDTO(post);
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
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        List<PostDTO> foundPostDTOs = postService.findAll();

        assertEquals(1, foundPostDTOs.size());
        verify(postRepository, times(1)).findAll();
        verify(postMapper, times(1)).toDTO(post);
    }

    @Test
    public void testSave() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toEntity(postDTO)).thenReturn(post);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO savedPostDTO = postService.save(postDTO);

        assertNotNull(savedPostDTO);
        assertEquals(postDTO.getTitle(), savedPostDTO.getTitle());
        verify(postRepository, times(1)).save(post);
        verify(postMapper, times(1)).toEntity(postDTO);
        verify(postMapper, times(1)).toDTO(post);
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
        when(postMapper.toDTO(post)).thenReturn(postDTO);
        when(postMapper.toEntity(postDTO)).thenReturn(post);

        postDTO.setTitle("Updated Title");
        PostDTO updatedPostDTO = postService.updatePost(postDTO);

        assertNotNull(updatedPostDTO);
        assertEquals("Updated Title", updatedPostDTO.getTitle());
        verify(postRepository, times(1)).findById(5L);
        verify(postRepository, times(1)).save(post);
        verify(postMapper, times(1)).toDTO(post);
    }

    @Test
    public void testUpdatePost_NotFound() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(postDTO));

        assertEquals("Post not found with id 5", exception.getMessage());
        verify(postRepository, times(1)).findById(5L);
        verify(postRepository, times(0)).save(any(Post.class));
    }

    @Test
    public void testFindPostsBeforeDate() {
        List<Post> posts = List.of(post);
        LocalDateTime date = now();
        when(postRepository.findByCreatedAtBefore(any(LocalDateTime.class))).thenReturn(posts);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        List<PostDTO> foundPostDTOs = postService.findPostsBeforeDate(date);

        assertNotNull(foundPostDTOs);
        assertEquals(1, foundPostDTOs.size());
        verify(postRepository, times(1)).findByCreatedAtBefore(date);
        verify(postMapper, times(1)).toDTO(post);
    }
}