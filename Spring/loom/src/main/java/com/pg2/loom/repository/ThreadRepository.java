package com.pg2.loom.repository;

import com.pg2.loom.entity.Thread;
import org.springframework.data.repository.CrudRepository;

public interface ThreadRepository extends CrudRepository<Thread, Long> {
    
}
