package com.example.myforum.service;

import java.util.List;
import com.example.myforum.model.Category;

public interface CategoryService {
    List<Category> findAllWithTopics();
}