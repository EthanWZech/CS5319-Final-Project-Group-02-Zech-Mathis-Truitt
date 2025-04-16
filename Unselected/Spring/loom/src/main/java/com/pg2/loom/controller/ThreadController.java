package com.pg2.loom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg2.loom.dto.*;
import com.pg2.loom.entity.Thread;
import com.pg2.loom.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/threads")
public class ThreadController {

    private final ThreadService threadService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/{id}")
    public TextMessage getThreadById(@PathVariable String id) throws JsonProcessingException {
        ThreadWithCommentsDto threadTree = threadService.getThreadAsTree(Long.valueOf(id));

        return new TextMessage(mapper.writeValueAsString(threadTree));

//        Optional<Thread> threadOptional = threadService.getThreadById(id);
//        return threadOptional.map(ResponseEntity::ok)
//                             .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/topic/{topic}")
    public List<Thread> getThreadsByTopic(@PathVariable String topic) {
        return threadService.getThreadsByTopic(topic);
    }

    @GetMapping("/recent")
    public TextMessage getRecentThreads() throws JsonProcessingException {
        HomeThreadsDto topThreads = new HomeThreadsDto(threadService.getMostRecentThreads());
        return new TextMessage(mapper.writeValueAsString(topThreads));
    }

    @GetMapping("/search")
    public List<Thread> searchThreads(@RequestParam String query) {
        return threadService.getThreadsMatchingTitle(query);
    }

    @PostMapping
    public Long createThread(@RequestBody AddThreadRequest thread) {
        return threadService.createThread(thread);
    }

    @PostMapping("/{id}/vote")
    public void voteThread(@PathVariable String id, @RequestBody VoteRequest voteRequest) {
        if(voteRequest.isVote())
            threadService.upvoteThread(Long.valueOf(id));
        else
            threadService.downvoteThread(Long.valueOf(id));

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
