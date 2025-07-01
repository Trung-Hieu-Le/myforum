package com.example.myforum.controller;

import com.example.myforum.exception.ResourceNotFoundException;
import com.example.myforum.model.Group;
import com.example.myforum.model.GroupMessage;
import com.example.myforum.model.User;
import com.example.myforum.service.GroupMessageService;
import com.example.myforum.service.GroupService;
import com.example.myforum.service.UserService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupMessageService groupMessageService;

    @GetMapping("/groups")
    public List<Group> getGroups(Principal principal) {
        try {
            return groupService.getGroupsForUser(principal.getName());
        } catch (Exception e) {
            // Log error and return empty list or handle as needed
            return List.of();
        }
    }

    @PostMapping("/groups")
    public ResponseEntity<?> createGroup(@RequestParam String name, Principal principal) {
        try {
            User user = userService.findByUsername(principal.getName());
            Group group = groupService.createGroup(name, user);
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            // Log error and return error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create group");
        }
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        try {
            Group group = groupService.getGroup(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id));
            return ResponseEntity.ok(group);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch group");
        }
    }

    @PostMapping("/groups/{groupId}/invite")
    public ResponseEntity<?> inviteUser(@PathVariable Long groupId, @RequestParam String username, Principal principal) {
        try {
            User user = userService.findByUsername(username);
            groupService.inviteUser(groupId, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to invite user");
        }
    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId, Principal principal) {
        try {
            User user = userService.findByUsername(principal.getName());
            boolean deleted = groupService.deleteGroup(groupId, user);
            if (deleted) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete group");
        }
    }

    @GetMapping("/groups/{groupId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long groupId) {
        try {
            List<GroupMessage> messages = groupMessageService.getMessages(groupId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch messages");
        }
    }
}
