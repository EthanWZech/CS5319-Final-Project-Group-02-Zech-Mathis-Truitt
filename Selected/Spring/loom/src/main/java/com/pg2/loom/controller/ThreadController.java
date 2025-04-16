package com.pg2.loom.controller;

import com.pg2.loom.dto.AddThreadRequest;
import com.pg2.loom.dto.ThreadDto;
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
    public List<ThreadDto> getRecentThreads() {
        return threadService.getMostRecentThreads();
    }

    @GetMapping("/search")
    public List<Thread> searchThreads(@RequestParam String query) {
        return threadService.getThreadsMatchingTitle(query);
    }

    @PostMapping
    public Long createThread(@RequestBody AddThreadRequest thread) {
        return threadService.createThread(thread);
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<Void> upvoteThread(@PathVariable Long id) {
        boolean success = threadService.upvoteThread(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<Void> downvoteThread(@PathVariable Long id) {
        boolean success = threadService.downvoteThread(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) {
        boolean deleted = threadService.deleteThreadById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
