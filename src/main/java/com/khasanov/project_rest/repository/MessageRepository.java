package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.story.id = :storyId")
    List<Message> findByStoryId(@Param("storyId") Long storyId);
}