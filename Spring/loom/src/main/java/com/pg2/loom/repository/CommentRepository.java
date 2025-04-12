package com.pg2.loom.repository;
import com.pg2.loom.entity.Comment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByThreadIdOrderByPublishDateDesc(Long id);
}
