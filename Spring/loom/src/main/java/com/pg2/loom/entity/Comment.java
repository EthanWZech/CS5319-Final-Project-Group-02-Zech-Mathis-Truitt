package com.pg2.loom.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date publishDate;

    private Integer upvotes;

    private Integer downvotes;

    private String username;

    private String text;

    private String image;

    @ManyToOne
    @JoinColumn(name = "threadId", nullable = false)
    private Thread thread;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;

    @ManyToOne
    @JoinColumn(name = "parentCommentId", nullable = true)
    private Comment parentComment;

    public Comment() {}

    public Comment(Date publishDate, Integer upvotes, Integer downvotes, String username, String text, String image, Thread thread, Comment parentComment) {
        replies = new ArrayList<Comment>();

        this.publishDate = publishDate;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.username = username;
        this.text = text;
        this.image = image;

        this.thread = thread;
        this.thread.addComment(this);

        if(parentComment != null) {
            this.parentComment = parentComment;
            parentComment.addReply(this);
        }
    }

    public void addReply(Comment reply) {
        replies.add(reply);
    }

    public Long getId() {
        return id;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public Thread getThread() {
        return thread;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public Comment getParentComment() {
        return parentComment;
    }
}
