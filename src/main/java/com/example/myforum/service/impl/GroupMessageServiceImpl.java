package com.example.myforum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myforum.model.GroupMessage;
import com.example.myforum.repository.GroupMessageRepository;
import com.example.myforum.service.GroupMessageService;

@Service
public class GroupMessageServiceImpl implements GroupMessageService {
    @Autowired
    private GroupMessageRepository groupMessageRepository;

    @Override
    public void saveMessage(GroupMessage message) {
        groupMessageRepository.save(message);
    }

    @Override
    public List<GroupMessage> getMessages(Long groupId) {
        return groupMessageRepository.findByGroupId(groupId);

    }

}
