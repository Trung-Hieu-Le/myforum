package com.example.myforum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.myforum.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query to fetch categories with their topics
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.topics")
    List<Category> findAllWithTopics();
}
