package com.example.springbootrestapi.service;

import com.example.springbootrestapi.payload.PostDto;
import com.example.springbootrestapi.payload.PostResponse;

public interface PostService {
    PostDto createPostDto(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePostById(Long id);
}
