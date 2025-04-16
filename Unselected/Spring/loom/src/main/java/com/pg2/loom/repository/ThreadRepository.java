package com.pg2.loom.repository;

import java.util.List;
import com.pg2.loom.entity.Thread;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ThreadRepository extends CrudRepository<Thread, Long> {
    List<Thread> findTop10ByOrderByPublishDateDesc();
    List<Thread> findByTopic(String topic);
    @Query("SELECT t FROM Thread t WHERE " +
           "LOWER(t.title) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<Thread> findByTitleContainingIgnoreCase(@Param("word") String word);
}
