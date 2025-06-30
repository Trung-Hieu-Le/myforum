package com.example.myforum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myforum.model.TopicCategory;

public interface TopicCategoryRepository extends JpaRepository<TopicCategory, Long> {
    @EntityGraph(attributePaths = { "topics" })
    List<TopicCategory> findAll();
}
