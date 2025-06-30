package com.example.myforum.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.myforum.model.Topic;

public interface TopicService {
    List<Topic> getTopicsByCategory(Long categoryId, Pageable pageable);

    Topic getById(Long topicId);
}
