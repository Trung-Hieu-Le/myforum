package com.example.myforum.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myforum.model.Group;
import com.example.myforum.model.User;
import com.example.myforum.repository.GroupRepository;
import com.example.myforum.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Group> getGroupsForUser(String username) {
        return groupRepository.findByMembers_Username(username);
    }

    @Override
    public Group createGroup(String name, User author) {
        Group group = new Group();
        group.setName(name);
        group.setAuthor(author);
        group.setMembers(new HashSet<>());
        group.getMembers().add(author);
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> getGroup(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public void inviteUser(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        group.getMembers().add(user);
        groupRepository.save(group);
    }

    @Override
    public boolean deleteGroup(Long groupId, User author) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isPresent() && groupOpt.get().getAuthor().equals(author)) {
            groupRepository.deleteById(groupId);
            return true;
        }
        return false;
    }
}