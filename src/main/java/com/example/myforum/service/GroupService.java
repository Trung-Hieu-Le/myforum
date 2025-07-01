package com.example.myforum.service;

import com.example.myforum.model.Group;
import com.example.myforum.model.User;

import java.util.Optional;
import java.util.List;

public interface GroupService {
    public List<Group> getGroupsForUser(String username);

    public Group createGroup(String name, User author);

    public Optional<Group> getGroup(Long id);

    public void inviteUser(Long groupId, User user);

    public boolean deleteGroup(Long groupId, User author);
}
