package com.example.myforum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.myforum.model.Post;
import com.example.myforum.model.Topic;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.updatedAt DESC")
    List<Post> findTrending(Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findLatest(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.author.username IN :followed ORDER BY p.createdAt DESC")
    List<Post> findByAuthors(List<String> followed, Pageable pageable);

    Page<Post> findAllByTopicOrderByUpdatedAtDesc(Topic topic, Pageable pageable);

    Page<Post> findByAuthorUsernameOrderByCreatedAtDesc(String username, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findRecent(Pageable pageable);
}
