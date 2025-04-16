package com.pg2.loom.dto;

import com.pg2.loom.entity.Thread;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter
public class ThreadWithCommentsDto implements TypedDto {
    private final String type = "ThreadWithCommentsDto";

    private Long id;

    private Date publishDate;

    private Integer upvotes;

    private Integer downvotes;

    private String topic;

    private String username;

    private String title;

    private String text;

    private String image;

    private List<CommentNodeDto> comments;

    public ThreadWithCommentsDto(Thread thread, List<CommentNodeDto> comments) {
        this.id = thread.getId();
        this.publishDate = thread.getPublishDate();
        this.upvotes = thread.getUpvotes();
        this.downvotes = thread.getDownvotes();
        this.topic = thread.getTopic();
        this.username = thread.getUsername();
        this.title = thread.getTitle();
        this.text = thread.getText();
        this.image = thread.getImage();
        this.comments = comments;
    }
}
