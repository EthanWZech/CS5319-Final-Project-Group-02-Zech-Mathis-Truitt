package com.pg2.loom.service;

import com.pg2.loom.dto.AddThreadRequest;
import com.pg2.loom.dto.CommentNodeDto;
import com.pg2.loom.dto.ThreadDto;
import com.pg2.loom.dto.ThreadWithCommentsDto;
import com.pg2.loom.entity.Comment;
import com.pg2.loom.entity.Thread;
import com.pg2.loom.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ThreadWithCommentsDto getThreadAsTree(Long id) {
        Thread thread = threadRepository.findById(id).orElseThrow();
        List<Comment> allComments = thread.getComments();
        List<CommentNodeDto> tree = buildCommentTree(allComments);

        return new ThreadWithCommentsDto(thread, tree);
    }

    public List<Thread> getThreadsByTopic(String topic) {
        return threadRepository.findByTopic(topic);
    }

    public List<ThreadDto> getMostRecentThreads() {
        List<Thread> threads = threadRepository.findTop10ByOrderByPublishDateDesc();
        List<ThreadDto> threadDtos = new ArrayList<>();

        for(Thread thread : threads) {
            threadDtos.add(new ThreadDto(thread));
        }

        return threadDtos;
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

    public Long createThread(AddThreadRequest thread) {
        Thread savedThread = threadRepository.save(new Thread(thread));
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

    public List<CommentNodeDto> buildCommentTree(List<Comment> comments) {
        Map<Long, CommentNodeDto> dtoMap = new HashMap<>();
        List<CommentNodeDto> roots = new ArrayList<>();

        for(Comment comment : comments) {
            dtoMap.put(comment.getId(), new CommentNodeDto(comment));
        }

        for(Comment comment : comments) {
            CommentNodeDto dto = dtoMap.get(comment.getId());
            if(comment.getParentComment() != null) {
                Long parentId = comment.getParentComment().getId();
                dtoMap.get(parentId).getReplies().add(dto);
            }
            else {
                roots.add(dto);
            }
        }

        return roots;
    }
}
