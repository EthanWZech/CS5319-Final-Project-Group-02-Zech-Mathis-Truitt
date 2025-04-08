package com.pg2.loom.service;

import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Collections;

@Service
public class ThreadService {

    private final ThreadRepository threadRepository;

    @Autowired
    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public Optional<Thread> getThreadById(Long id) {
        return threadRepository.findById(id);
    }

    public List<Thread> getThreadsByTopic(String topic) {
        return threadRepository.findByTopic(topic);
    }

    public List<Thread> getMostRecentThreads() {
        return threadRepository.findTop10ByOrderByPublishDateDesc();
    }

    public List<Thread> getThreadsMatchingTitle(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String[] words = input.toLowerCase().split("\\s+");
        Set<Thread> resultSet = new HashSet<>();

        for (String word : words) {
            List<Thread> threads = threadRepository.findByTitleContainingIgnoreCase(word);
            resultSet.addAll(threads); // Avoid duplicates
        }

        return new ArrayList<>(resultSet);
    }

    public Long createThread(Thread thread) {
        Thread savedThread = threadRepository.save(thread);
        return savedThread.getId();
    }

    public boolean upvoteThread(Long id) {
        Optional<Thread> optional = threadRepository.findById(id);
        if (optional.isPresent()) {
            Thread thread = optional.get();
            thread.setUpvotes(thread.getUpvotes() + 1);
            threadRepository.save(thread);
            return true;
        }
        return false;
    }
    
    public boolean downvoteThread(Long id) {
        Optional<Thread> optional = threadRepository.findById(id);
        if (optional.isPresent()) {
            Thread thread = optional.get();
            thread.setDownvotes(thread.getDownvotes() + 1);
            threadRepository.save(thread);
            return true;
        }
        return false;
    }

    public boolean deleteThreadById(Long id) {
        Optional<Thread> optional = threadRepository.findById(id);
        if (optional.isPresent()) {
            threadRepository.delete(optional.get());
            return true;
        }
        return false;
    }    
}
