package com.pg2.loom.service;

import com.pg2.loom.entity.Comment;
import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.CommentRepository;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ThreadRepository threadRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ThreadRepository threadRepository) {
        this.commentRepository = commentRepository;
        this.threadRepository = threadRepository;
    }

    public Long addCommentToThread(Long threadId, String username, String text, String image) {
        Optional<Thread> threadOptional = threadRepository.findById(threadId);
        
        if (threadOptional.isPresent()) {
            Thread thread = threadOptional.get();
            
            Comment comment = new Comment(
                new Date(System.currentTimeMillis()),
                0,
                0,
                username,
                text,
                image,
                thread,
                null
            );
            
            Comment savedComment = commentRepository.save(comment);
            return savedComment.getId();
        }
        
        
        throw new IllegalArgumentException("Thread with ID " + threadId + " not found");
    }
    
    public Long addReplyToComment(Long threadId, Long parentCommentId, String username, String text, String image) {
        Optional<Thread> threadOptional = threadRepository.findById(threadId);
        Optional<Comment> parentCommentOptional = commentRepository.findById(parentCommentId);
        
        if (threadOptional.isPresent() && parentCommentOptional.isPresent()) {
            Thread thread = threadOptional.get();
            Comment parentComment = parentCommentOptional.get();
            
            
            Comment reply = new Comment(
                new Date(System.currentTimeMillis()),
                0,
                0,
                username,
                text,
                image,
                thread,
                parentComment
            );
            
            Comment savedReply = commentRepository.save(reply);
            return savedReply.getId();
        }
        
        
        throw new IllegalArgumentException("Thread or parent comment not found");
    }
    
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public boolean upvoteComment(Long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();
            comment.setUpvotes(comment.getUpvotes() + 1);
            commentRepository.save(comment);
            return true;
        }
        return false;
    }
    
    public boolean downvoteComment(Long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();
            comment.setDownvotes(comment.getDownvotes() + 1);
            commentRepository.save(comment);
            return true;
        }
        return false;
    }
    
}