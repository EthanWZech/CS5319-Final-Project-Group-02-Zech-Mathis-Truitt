package com.pg2.loom.repository;
import com.pg2.loom.entity.Comment;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    
}
