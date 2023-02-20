package com.example.springbootrestapi.payload;

import lombok.Data;

@Data
public class CommentDTO {
    private long id;
    private String name;
    private String mail;
    private String body;
}
