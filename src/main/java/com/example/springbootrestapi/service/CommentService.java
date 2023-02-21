package com.example.springbootrestapi.service;

import com.example.springbootrestapi.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsByPost(long postId);

    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);
}
