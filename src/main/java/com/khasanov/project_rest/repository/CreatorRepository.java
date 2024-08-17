package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
    @Query("SELECT c FROM Story s JOIN Creator c ON s.creator.id = :storyId")
    Creator findByStoryId(Long storyId);
}
