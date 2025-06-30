package com.example.myforum.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myforum.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCategoryIdOrderByLastUpdatedAtDesc(Long categoryId, Pageable pageable);
}
