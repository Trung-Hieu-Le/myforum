package com.example.myforum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.List;

import com.example.myforum.service.CategoryService;
import com.example.myforum.service.PostService;
import com.example.myforum.model.Category;
import com.example.myforum.model.Post;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        List<Category> categories = categoryService.findAllWithTopics();
        List<Post> trendingPosts = postService.findTrendingPosts();
        List<Post> latestPosts = postService.findLatestPosts();
        List<Post> followedPosts = principal != null
                ? postService.findPostsFromFollowedUsers(principal.getName())
                : List.of();

        model.addAttribute("categories", categories);
        model.addAttribute("trendingPosts", trendingPosts);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("followedPosts", followedPosts);

        return "client/home";
    }
}