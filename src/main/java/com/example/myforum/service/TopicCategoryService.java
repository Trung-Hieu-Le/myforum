package com.example.myforum.service;

import java.util.List;
import com.example.myforum.model.TopicCategory;

public interface TopicCategoryService {
    List<TopicCategory> findAllWithTopics();
}