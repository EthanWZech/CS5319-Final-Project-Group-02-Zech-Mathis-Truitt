package com.pg2.loom.dto;

import lombok.Getter;

@Getter
public class AddCommentRequest {
    private Long threadId;

    private Long parentCommentId;

    private String username;

    private String text;

    private String image;

    public AddCommentRequest() {}

    public AddCommentRequest(Long threadId, Long parentCommentId, String username, String text, String image) {
        this.threadId = threadId;
        this.parentCommentId = parentCommentId;
        this.username = username;
        this.text = text;
        this.image = image;
    }
}
