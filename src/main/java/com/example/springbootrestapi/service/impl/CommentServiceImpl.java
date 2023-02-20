package com.example.springbootrestapi.service.impl;

import com.example.springbootrestapi.entity.Comment;
import com.example.springbootrestapi.entity.Post;
import com.example.springbootrestapi.exception.CommentNotMatchPostException;
import com.example.springbootrestapi.exception.ResourceNotFoundException;
import com.example.springbootrestapi.payload.CommentDTO;
import com.example.springbootrestapi.repository.CommentRepository;
import com.example.springbootrestapi.repository.PostRepository;
import com.example.springbootrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        // retrieve post entity by id
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()){
            Post post = postOptional.get();
            comment.setPost(post);
            Comment newComment = commentRepository.save(comment);
            return mapToDTO(newComment);
        } else throw new ResourceNotFoundException("Comment", "id", String.valueOf(postId));
    }

    @Override
    public List<CommentDTO> getAllCommentsByPost(long postId) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        return commentList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            if (comment.getPost().getId() == postId) return mapToDTO(comment);
            else throw new CommentNotMatchPostException("Comment", "id" , String.valueOf(commentId), "Post", "id", String.valueOf(postId));
        }else throw new ResourceNotFoundException("Comment", "id", String.valueOf(commentId));
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            if (comment.getPost().getId() == postId) {
                comment.setBody(commentDTO.getBody());
                comment.setName(commentDTO.getName());
                comment.setEmail(commentDTO.getMail());
                Comment updatedComment = commentRepository.save(comment);
                return mapToDTO(updatedComment);
            } else throw new CommentNotMatchPostException("Comment", "id" , String.valueOf(commentId), "Post", "id", String.valueOf(postId));
        }else throw new ResourceNotFoundException("Comment", "id", String.valueOf(commentId));
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody(comment.getBody());
        commentDTO.setMail(comment.getEmail());
        commentDTO.setName(comment.getName());
        commentDTO.setId(comment.getId());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setEmail(commentDTO.getMail());
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
