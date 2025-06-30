package com.example.myforum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myforum.model.TopicCategory;
import com.example.myforum.repository.TopicCategoryRepository;
import com.example.myforum.service.TopicCategoryService;

@Service
public class TopicCategoryServiceImpl implements TopicCategoryService {
    @Autowired
    private TopicCategoryRepository categoryRepo;

    @Override
    public List<TopicCategory> findAllWithTopics() {
        return categoryRepo.findAll();
    }
}
