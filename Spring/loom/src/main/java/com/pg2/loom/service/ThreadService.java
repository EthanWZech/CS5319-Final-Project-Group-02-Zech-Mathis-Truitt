package com.pg2.loom.service;

import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Thread> getMostRecentThreads() {
        return threadRepository.findTop10ByOrderByPublishDateDesc();
    }

    public Long createThread(Thread thread) {
        Thread savedThread = threadRepository.save(thread);
        return savedThread.getId();
    }    
}
