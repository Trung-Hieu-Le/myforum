package com.example.myforum.service;

import java.util.List;
import com.example.myforum.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    public List<Category> findAllWithTopics();
}