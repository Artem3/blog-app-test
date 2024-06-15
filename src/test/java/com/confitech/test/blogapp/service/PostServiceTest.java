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

import com.confitech.test.blogapp.dto.CreatePostDTO;
import com.confitech.test.blogapp.dto.PostDTO;
import com.confitech.test.blogapp.dto.UpdatePostDTO;
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
    private PostDTO updatedPostDTO;
    private UpdatePostDTO updatePostDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        post = new Post();
        post.setId(5L);
        post.setTitle("Test Title");
        post.setMessage("Test Message");
        post.setCategory("Test Category");

        postDTO = new PostDTO();
        postDTO.setId(5L);
        postDTO.setTitle("Test Title");
        postDTO.setMessage("Test Message");
        postDTO.setCategory("Test Category");

        updatedPostDTO = new PostDTO();
        updatedPostDTO.setId(6L);
        updatedPostDTO.setTitle("6Updated Title");
        updatedPostDTO.setMessage("6Updated Message");
        updatedPostDTO.setCategory("6Updated Category");

        updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setId(7L);
        updatePostDTO.setMessage("7Updated Title");
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
    public void testSave() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setTitle("Test Title");
        createPostDTO.setMessage("Test Message");
        createPostDTO.setCategory("Test Category");

        Post post = new Post();
        post.setTitle(createPostDTO.getTitle());
        post.setMessage(createPostDTO.getMessage());
        post.setCategory(createPostDTO.getCategory());

        when(postMapper.toEntity(createPostDTO)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO savedPostDTO = postService.save(createPostDTO);

        assertNotNull(savedPostDTO);
        assertEquals(post.getTitle(), savedPostDTO.getTitle());
        verify(postRepository, times(1)).save(any(Post.class));
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
        UpdatePostDTO updateDTO = new UpdatePostDTO();
        updateDTO.setId(6L);
        updateDTO.setTitle("6Updated Title");
        updateDTO.setMessage("6Updated Message");
        updateDTO.setCategory("6Updated Category");

        Post existingPost = new Post();
        existingPost.setTitle("existing Title");
        existingPost.setMessage("existing Message");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(existingPost);
        when(postMapper.toDTO(existingPost)).thenReturn(updatedPostDTO);

        PostDTO result = postService.updatePost(updateDTO);

        assertNotNull(result);
        assertEquals("6Updated Title", result.getTitle());
        assertEquals("6Updated Message", result.getMessage());
        assertEquals("6Updated Category", result.getCategory());
        verify(postRepository, times(1)).findById(6L);
        verify(postRepository, times(1)).save(existingPost);
    }

    @Test
    public void testUpdatePost_NotFound() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception =
                assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(updatePostDTO));

        assertEquals("Post not found with id 7", exception.getMessage());
        verify(postRepository, times(1)).findById(7L);
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