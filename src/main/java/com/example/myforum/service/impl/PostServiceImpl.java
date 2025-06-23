package com.example.myforum.service.impl;

import com.example.myforum.model.Media;
import com.example.myforum.model.Post;
import com.example.myforum.model.Topic;
import com.example.myforum.model.User;
import com.example.myforum.repository.PostRepository;
import com.example.myforum.repository.TopicRepository;
import com.example.myforum.service.PostService;
import com.example.myforum.service.StorageService;
import com.example.myforum.config.PaginationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.myforum.service.UserService;



@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private TopicRepository topicRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StorageService storageService; // Assume this is a service to handle file storage


    private static final int TRENDING_LIMIT = PaginationConfig.TRENDING_SIZE;
    private static final int LATEST_LIMIT = PaginationConfig.LATEST_SIZE;

    @Override
    public List<Post> findTrendingPosts() {
        return postRepo.findTrending(PageRequest.of(0, TRENDING_LIMIT));
    }

    @Override
    public List<Post> findLatestPosts() {
        return postRepo.findLatest(PageRequest.of(0, LATEST_LIMIT));
    }

    @Override
    public List<Post> findPostsFromFollowedUsers(String username) {
        // fetch list of followed usernames from somewhere (e.g. UserService)
        List<String> followed = /* ... */ List.of();
        return postRepo.findByAuthors(followed, PageRequest.of(0, LATEST_LIMIT));
    }

    @Override
    public Page<Post> getPostsByTopic(Long topicId, Pageable pageable) {
        Topic topic = topicRepo.findById(topicId).orElseThrow();
        return postRepo.findAllByTopicOrderByUpdatedAtDesc(topic, pageable);
    }

    @Override
    public Page<Post> getPostsByAuthor(String username, Pageable pageable) {
        return postRepo.findByAuthorUsernameOrderByCreatedAtDesc(username, pageable);
    }

    @Override
    @Transactional
    public Post createPost(String username, String content, List<MultipartFile> files) {
        User u = userService.findByUsername(username);
        Post p = new Post(); p.setAuthor(u); p.setContent(content);
        p.setCreatedAt(LocalDateTime.now()); p.setUpdatedAt(LocalDateTime.now());
        List<Media> mediaList = files.stream().map(f -> {
            // save file somewhere and get URL
            String url = storageService.store(f);
            Media m = new Media(); m.setUrl(url); m.setType(detectType(f)); m.setPost(p);
            return m;
        }).collect(Collectors.toList());
        p.setMedia(mediaList);
        Post saved = postRepo.save(p);
        // notify via websocket
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/posts", saved);
        return saved;
    }

    @Override
    public void incrementView(Long postId) {
        postRepo.findById(postId).ifPresent(p -> {
            p.setViewCount(p.getViewCount() + 1);
            postRepo.save(p);
            simpMessagingTemplate.convertAndSend("/topic/viewCount/"+postId, p.getViewCount());
        });
    }

    @Override
    public void incrementComment(Long postId) {
        postRepo.findById(postId).ifPresent(p -> {
            p.setCommentCount(p.getCommentCount() + 1);
            postRepo.save(p);
            simpMessagingTemplate.convertAndSend("/topic/commentCount/"+postId, p.getCommentCount());
        });
    }

    private String detectType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null && contentType.startsWith("image/")) {
            return "image";
        } else if (contentType != null && contentType.startsWith("video/")) {
            return "video";
        } else {
            return "file"; // default type
        }
    }
}
