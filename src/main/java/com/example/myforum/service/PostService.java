package com.example.myforum.service;

import com.example.myforum.model.Post;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    public List<Post> findTrendingPosts();

    public List<Post> findLatestPosts();

    public List<Post> findPostsFromFollowedUsers(String username);
}
