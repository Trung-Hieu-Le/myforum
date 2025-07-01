package com.example.myforum.service;

import java.util.List;

import com.example.myforum.model.GroupMessage;

public interface GroupMessageService {
    public void saveMessage(GroupMessage message);

    public List<GroupMessage> getMessages(Long groupId);
}
