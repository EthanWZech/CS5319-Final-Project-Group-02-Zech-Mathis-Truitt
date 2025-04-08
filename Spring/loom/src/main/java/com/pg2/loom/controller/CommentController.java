package com.pg2.loom.controller;

import com.pg2.loom.entity.Comment;
import com.pg2.loom.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        return commentOptional.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/thread/{threadId}")
    public ResponseEntity<Long> addCommentToThread(
            @PathVariable Long threadId,
            @RequestBody Map<String, String> payload) {
        
        try {
            String username = payload.get("username");
            String text = payload.get("text");
            String image = payload.get("image");
            
            Long commentId = commentService.addCommentToThread(threadId, username, text, image);
            return ResponseEntity.ok(commentId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/thread/{threadId}/comment/{commentId}/reply")
    public ResponseEntity<Long> addReplyToComment(
            @PathVariable Long threadId,
            @PathVariable Long commentId,
            @RequestBody Map<String, String> payload) {
        
        try {
            String username = payload.get("username");
            String text = payload.get("text");
            String image = payload.get("image");
            
            Long replyId = commentService.addReplyToComment(threadId, commentId, username, text, image);
            return ResponseEntity.ok(replyId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<Void> upvoteComment(@PathVariable Long id) {
        boolean success = commentService.upvoteComment(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<Void> downvoteComment(@PathVariable Long id) {
        boolean success = commentService.downvoteComment(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
