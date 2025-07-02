package com.example.myforum.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.myforum.config.PaginationConfig;
import com.example.myforum.dto.PasswordChangeDto;
import com.example.myforum.dto.UserProfileDto;
import com.example.myforum.model.Post;
import com.example.myforum.model.TopicCategory;
import com.example.myforum.service.PostService;
import com.example.myforum.service.TopicCategoryService;
import com.example.myforum.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private TopicCategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping({ "/", "/home" })
    public String home(Model model, Principal principal) {
        try {
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
        } catch (Exception ex) {
            logger.error("Error loading home page", ex);
            model.addAttribute("errorMessage", "An error occurred while loading the home page.");
            return "error";
        }
    }

    @GetMapping("/setting")
    public String showSettings(Model model, Principal principal) {
        try {
            String username = principal.getName();
            model.addAttribute("profileForm", userService.getProfileDto(username));
            model.addAttribute("passwordForm", new PasswordChangeDto());
            return "client/setting";
        } catch (Exception ex) {
            logger.error("Error loading settings page", ex);
            model.addAttribute("errorMessage", "An error occurred while loading settings.");
            return "error";
        }
    }

    @PostMapping("/setting/profile")
    public String updateProfile(
            @Valid @ModelAttribute("profileForm") UserProfileDto profileForm,
            BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "client/setting";
        }
        try {
            userService.updateProfile(principal.getName(), profileForm);
            return "redirect:/setting?success";
        } catch (Exception ex) {
            logger.error("Error updating profile", ex);
            result.reject("profile.update.error", "An error occurred while updating your profile.");
            return "client/setting";
        }
    }

    @PostMapping("/setting/password")
    public String changePassword(
            @Valid @ModelAttribute("passwordForm") PasswordChangeDto passwordForm,
            BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "client/setting";
        }
        try {
            userService.changePassword(principal.getName(), passwordForm);
            return "redirect:/setting?success";
        } catch (Exception ex) {
            logger.error("Error changing password", ex);
            result.reject("password.change.error", "An error occurred while changing your password.");
            return "client/setting";
        }
    }

    @GetMapping("/wall")
    public String wallPage(Model model, Principal principal,
            @RequestParam(defaultValue = "0") int page) {
        try {
            String user = principal.getName();
            model.addAttribute("posts",
                    postService.getPostsByAuthor(user, PageRequest.of(page, PaginationConfig.POSTS_PER_PAGE)));
            return "client/wall";
        } catch (Exception ex) {
            logger.error("Error loading wall page", ex);
            model.addAttribute("errorMessage", "An error occurred while loading your wall.");
            return "error";
        }
    }

    @PostMapping("/wall/new")
    public String submitPost(@RequestParam String content,
            @RequestParam(required = false) List<MultipartFile> files,
            Principal principal) {
        try {
            postService.createPost(principal.getName(), content, files);
            return "redirect:/wall";
        } catch (Exception ex) {
            logger.error("Error submitting new post", ex);
            return "redirect:/wall?error";
        }
    }

    @PostMapping("/locks")
    public String updateLocks(@ModelAttribute("profileForm") UserProfileDto profileForm,
            Principal principal) {
        try {
            userService.updateLocks(principal.getName(), profileForm.isLockComments(), profileForm.isLockProfile());
            return "redirect:/setting?success";
        } catch (Exception ex) {
            logger.error("Error updating locks", ex);
            return "redirect:/setting?error";
        }
    }

    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            return csrfToken;
        } catch (Exception ex) {
            logger.error("Error retrieving CSRF token", ex);
            model.addAttribute("errorMessage", "An error occurred while retrieving the CSRF token.");
            return null;
        }
    }
}