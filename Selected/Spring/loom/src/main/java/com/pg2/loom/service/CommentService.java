package com.pg2.loom.service;

import com.pg2.loom.dto.AddCommentRequest;
import com.pg2.loom.entity.Comment;
import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.CommentRepository;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
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

    public Long addCommentToThread(AddCommentRequest request) {
        Optional<Thread> threadOptional = threadRepository.findById(request.getThreadId());
        
        if (threadOptional.isPresent()) {
            Thread thread = threadOptional.get();
            
            Comment comment = new Comment(
                new Date(System.currentTimeMillis()),
                0,
                0,
                    request.getUsername(),
                    request.getText(),
                    request.getImage(),
                thread,
                null
            );
            
            Comment savedComment = commentRepository.save(comment);
            return savedComment.getId();
        }
        
        
        throw new IllegalArgumentException("Thread with ID " + request.getThreadId() + " not found");
    }
    
    public Long addReplyToComment(AddCommentRequest request) {
        Optional<Thread> threadOptional = threadRepository.findById(request.getThreadId());
        Optional<Comment> parentCommentOptional = commentRepository.findById(request.getParentCommentId());
        
        if (threadOptional.isPresent() && parentCommentOptional.isPresent()) {
            Thread thread = threadOptional.get();
            Comment parentComment = parentCommentOptional.get();
            
            
            Comment reply = new Comment(
                new Date(System.currentTimeMillis()),
                0,
                0,
                    request.getUsername(),
                    request.getText(),
                    request.getImage(),
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

    public List<Comment> getCommentsByThreadId(Long threadId) {
        return commentRepository.findByThreadIdOrderByPublishDateDesc(threadId);
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