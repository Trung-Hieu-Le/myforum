package com.example.myforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.myforum.model.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTrendingPosts();
    
    List<Post> findLatestPosts();
    
    List<Post> findPostsFromFollowedUsers(String username);
    
}
