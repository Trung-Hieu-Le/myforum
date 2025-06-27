package com.example.myforum.controller;

import com.example.myforum.config.PaginationConfig;
import com.example.myforum.dto.PasswordChangeDto;
import com.example.myforum.dto.UserProfileDto;
import com.example.myforum.model.TopicCategory;
import com.example.myforum.model.Post;
import com.example.myforum.service.TopicCategoryService;
import com.example.myforum.service.PostService;
import com.example.myforum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private TopicCategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        List<TopicCategory> categories = categoryService.findAllWithTopics();
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

    @GetMapping("/setting")
    public String showSettings(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("profileForm", userService.getProfileDto(username));
        model.addAttribute("passwordForm", new PasswordChangeDto());
        return "client/setting";
    }

    @PostMapping("/setting/profile")
    public String updateProfile(
            @Valid @ModelAttribute("profileForm") UserProfileDto profileForm,
            BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "client/setting";
        }
        userService.updateProfile(principal.getName(), profileForm);
        return "redirect:/setting?success";
    }

    @PostMapping("/setting/password")
    public String changePassword(
            @Valid @ModelAttribute("passwordForm") PasswordChangeDto passwordForm,
            BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "client/setting";
        }
        userService.changePassword(principal.getName(), passwordForm);
        return "redirect:/setting?success";
    }

    @GetMapping("/wall")
    public String wallPage(Model model, Principal principal,
            @RequestParam(defaultValue = "0") int page) {
        String user = principal.getName();
        model.addAttribute("posts",
                postService.getPostsByAuthor(user, PageRequest.of(page, PaginationConfig.POSTS_PER_PAGE)));
        return "client/wall";
    }

    @PostMapping("/wall/new")
    public String submitPost(@RequestParam String content,
            @RequestParam(required = false) List<MultipartFile> files,
            Principal principal) {
        postService.createPost(principal.getName(), content, files);
        return "redirect:/wall";
    }

    @PostMapping("/locks")
    public String updateLocks(@ModelAttribute("profileForm") UserProfileDto profileForm,
            Principal principal) {
        userService.updateLocks(principal.getName(), profileForm.isLockComments(), profileForm.isLockProfile());
        return "redirect:/setting?success";
    }

}