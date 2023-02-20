package com.example.springbootrestapi.service.impl;

import com.example.springbootrestapi.entity.Post;
import com.example.springbootrestapi.exception.ResourceNotFoundException;
import com.example.springbootrestapi.payload.PostDto;
import com.example.springbootrestapi.payload.PostResponse;
import com.example.springbootrestapi.repository.PostRepository;
import com.example.springbootrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPostDto(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent();
        List<PostDto> postDtoList = postList.stream().map(this::mapToDTO).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setLast(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()){
            return mapToDTO(post.get());
        } else throw new ResourceNotFoundException("Post", "id", String.valueOf(id));

    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        // Get post by id to update
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()){
            Post post = postOptional.get();
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
            post.setTitle(postDto.getTitle());

            Post updatedPost = postRepository.save(post);
            return mapToDTO(updatedPost);

        } else throw new ResourceNotFoundException("Post", "id", String.valueOf(id));
    }

    @Override
    public void deletePostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            postRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Post", "id", String.valueOf(id));
    }

    // Convert entity to DTO
    private PostDto mapToDTO(Post post){

        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setDescription(post.getDescription());
        postResponse.setContent(post.getContent());
        postResponse.setTitle(post.getTitle());

        return postResponse;
    }

    // Convert DTO to entity
    private Post mapToEntity(PostDto postDto){

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return post;
    }
}
