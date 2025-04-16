package com.pg2.loom.dto;

import com.pg2.loom.entity.Comment;
import lombok.Getter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentNodeDto {
    private Long id;

    private Date publishDate;

    private Integer upvotes;

    private Integer downvotes;

    private String username;

    private String text;

    private String image;

    private Long parentCommentId;

    private List<CommentNodeDto> replies = new ArrayList<CommentNodeDto>();

    public CommentNodeDto(Comment comment) {
        this.id = comment.getId();
        this.publishDate = comment.getPublishDate();
        this.upvotes = comment.getUpvotes();
        this.downvotes = comment.getDownvotes();
        this.username = comment.getUsername();
        this.text = comment.getText();
        this.image = comment.getImage();
        this.parentCommentId = comment.getParentComment() != null ? comment.getParentComment().getId() : null;
    }

}
