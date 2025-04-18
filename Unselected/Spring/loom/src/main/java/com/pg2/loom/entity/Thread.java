package com.pg2.loom.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pg2.loom.dto.AddThreadRequest;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date publishDate;

    private Integer upvotes;

    private Integer downvotes;

    private String topic;

    private String username;

    private String title;

    private String text;

    private String image;

    @OneToMany(mappedBy="thread", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Comment> comments;

    public Thread() {}

    public Thread(Date publishDate, Integer upvotes, Integer downvotes, String topic, String username, String title, String text, String image) {
        comments = new ArrayList<Comment>();

        this.publishDate = publishDate;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.topic = topic;
        this.username = username;
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public Thread(String topic, String username, String title, String text, String image) {
        comments = new ArrayList<Comment>();

        this.publishDate = new Date(System.currentTimeMillis());
        this.upvotes = 0;
        this.downvotes = 0;
        this.topic = topic;
        this.username = username;
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public Thread(AddThreadRequest thread) {
        comments = new ArrayList<Comment>();

        this.publishDate = new Date(System.currentTimeMillis());
        this.upvotes = 0;
        this.downvotes = 0;
        this.topic = thread.getTopic();
        this.username = thread.getUsername();
        this.title = thread.getTitle();
        this.text = thread.getText();
        this.image = thread.getImage();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public Long getId() {
        return id;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public String getTopic() {
        return topic;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }
    
    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }    
}
