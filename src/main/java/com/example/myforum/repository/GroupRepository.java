package com.example.myforum.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myforum.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembers_Username(String username);

}
