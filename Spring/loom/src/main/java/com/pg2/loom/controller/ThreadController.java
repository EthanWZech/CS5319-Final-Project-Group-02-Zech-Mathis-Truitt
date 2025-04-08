package com.pg2.loom.controller;

import com.pg2.loom.entity.Thread;
import com.pg2.loom.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/topic/{topic}")
    public List<Thread> getThreadsByTopic(@PathVariable String topic) {
        return threadService.getThreadsByTopic(topic);
    }

    @GetMapping("/recent")
    public List<Thread> getRecentThreads() {
        return threadService.getMostRecentThreads();
    }

    @GetMapping("/search")
    public List<Thread> searchThreads(@RequestParam String query) {
        return threadService.getThreadsMatchingTitle(query);
    }

    @PostMapping
    public Long createThread(@RequestBody Thread thread) {
        return threadService.createThread(thread);
    }
}
