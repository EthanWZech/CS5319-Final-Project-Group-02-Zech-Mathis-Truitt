package com.pg2.loom.controller;

import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Thread> getThreadById(@PathVariable Long id) {
        Optional<Thread> thread = threadRepository.findById(id);
        return thread.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
