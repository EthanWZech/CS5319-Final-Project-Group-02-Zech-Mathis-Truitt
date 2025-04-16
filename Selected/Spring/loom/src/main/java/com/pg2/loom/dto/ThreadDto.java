package com.pg2.loom.dto;

import com.pg2.loom.entity.Thread;
import lombok.Getter;

import java.sql.Date;

@Getter
public class ThreadDto implements TypedDto {
    private final String type = "ThreadDto";

    private Long id;

    private Date publishDate;

    private Integer upvotes;

    private Integer downvotes;

    private String topic;

    private String username;

    private String title;

    private String text;

    private String image;

    public ThreadDto(Thread thread) {
        this.id = thread.getId();
        this.publishDate = thread.getPublishDate();
        this.upvotes = thread.getUpvotes();
        this.downvotes = thread.getDownvotes();
        this.topic = thread.getTopic();
        this.username = thread.getUsername();
        this.title = thread.getTitle();
        this.text = thread.getText();
        this.image = thread.getImage();
    }
}
