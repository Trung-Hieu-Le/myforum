package com.example.myforum.service.impl;

import com.example.myforum.service.CategoryService;
import com.example.myforum.model.Category;
import com.example.myforum.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllWithTopics() {
        return categoryRepository.findAllWithTopics();
    }
}
