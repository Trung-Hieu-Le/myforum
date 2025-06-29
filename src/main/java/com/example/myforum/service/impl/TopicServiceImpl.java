package com.example.myforum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myforum.model.Topic;
import com.example.myforum.repository.TopicRepository;
import com.example.myforum.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepo;

    @Override
    public List<Topic> getTopicsByCategory(Long categoryId, Pageable pageable) {
        return topicRepo.findByCategoryIdOrderByLastUpdatedAtDesc(categoryId, pageable);
    }

    @Override
    public Topic getById(Long topicId) {
        return topicRepo.findById(topicId).orElseThrow();
    }
}
