package com.example.myforum.service;

import com.example.myforum.model.Topic;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TopicService {
    List<Topic> getTopicsByCategory(Long categoryId, Pageable pageable);
    Topic getById(Long topicId);
}
