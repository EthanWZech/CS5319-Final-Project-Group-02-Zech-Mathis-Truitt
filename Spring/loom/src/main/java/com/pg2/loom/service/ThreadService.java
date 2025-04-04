package com.pg2.loom.service;

import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
