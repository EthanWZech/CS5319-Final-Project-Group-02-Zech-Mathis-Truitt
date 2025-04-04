package com.pg2.loom.controller;

import com.pg2.loom.entity.Thread;
import com.pg2.loom.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thread> getThreadById(@PathVariable Long id) {
        Optional<Thread> threadOptional = threadService.getThreadById(id);
        return threadOptional.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }
}
