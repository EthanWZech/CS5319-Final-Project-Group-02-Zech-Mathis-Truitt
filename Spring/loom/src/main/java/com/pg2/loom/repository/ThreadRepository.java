package com.pg2.loom.repository;

import java.util.List;
import com.pg2.loom.entity.Thread;
import org.springframework.data.repository.CrudRepository;

public interface ThreadRepository extends CrudRepository<Thread, Long> {
    List<Thread> findTop10ByOrderByPublishDateDesc();
    List<Thread> findByTopic(String topic);
}
