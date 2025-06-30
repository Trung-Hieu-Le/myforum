package com.example.myforum.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.myforum.model.Post;

public interface PostService {
    List<Post> findTrendingPosts();

    List<Post> findLatestPosts();

    List<Post> findPostsFromFollowedUsers(String username);

    Page<Post> getPostsByTopic(Long topicId, Pageable pageable);

    Page<Post> getPostsByAuthor(String username, Pageable pageable);

    Post createPost(String username, String content, List<MultipartFile> files);

    void incrementView(Long postId);

    void incrementComment(Long postId);
}
