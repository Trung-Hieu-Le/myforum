package com.example.myforum.service.impl;

import com.example.myforum.model.Post;
import com.example.myforum.repository.PostRepository;
import com.example.myforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findTrendingPosts() {
        return postRepository.findTrendingPosts();
    }

    @Override
    public List<Post> findLatestPosts() {
        return postRepository.findLatestPosts();
    }

    @Override
    public List<Post> findPostsFromFollowedUsers(String username) {
        return postRepository.findPostsFromFollowedUsers(username);
    }
}
